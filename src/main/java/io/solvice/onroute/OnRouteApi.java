package io.solvice.onroute;

import io.solvice.ApiClient;
import io.solvice.ApiException;
import io.solvice.Configuration;
import io.solvice.onroute.internal.JobsApi;
import io.solvice.onroute.internal.RoutingApi;
import io.solvice.onroute.internal.SolutionApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;


public abstract class OnRouteApi {

    private final Logger log = LoggerFactory.getLogger(OnRouteApi.class);

    private static int SOLVED_POLL_PERIOD = 900; // MS
    private static int QUEUE_POLL_PERIOD = 300; // MS
    private static int NB_RETRIES = 20;
    private final RoutingApi routingApi;
    private final JobsApi jobsApi;
    private final SolutionApi solutionApi;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(50);
    private ApiClient client;


    public OnRouteApi(ApiClient client) {
        this.client = Configuration.getDefaultApiClient();
        this.routingApi = new RoutingApi(client);
        this.jobsApi = new JobsApi(client);
        this.solutionApi = new SolutionApi(client);
        this.client = client;
    }


    @Nullable
    public Job solve(SolveRequest request) {
        try {
            Job job = routingApi.solve(request);
            log.debug("Sent request {} to server {}" , job.getId(), client.getBasePath());
            return job;
        } catch (ApiException e) {
            log.error("Solve error: "+ e.getMessage());
            return null;
        }
    }

    public RoutingSolution getRoutingSolution(Job job) throws ApiException {
        return solutionApi.getSolution(job.getId());
    }



    public CompletableFuture<Job> pollSolving(@NotNull Job job) {
        CompletableFuture<Job> solvingFuture = new CompletableFuture<>();
        final UUID solveId = job.getId();
        Runnable pollSolving = getPollIfSolving(job, solvingFuture, solveId);
        final ScheduledFuture<?> checkPolling = scheduler.scheduleWithFixedDelay(pollSolving, 0, QUEUE_POLL_PERIOD, TimeUnit.MILLISECONDS);
        solvingFuture.whenComplete((result, thrown) -> checkPolling.cancel(false))
                .exceptionally(this::handleNotCompletedJobException);
        return solvingFuture;
    }

    public CompletableFuture<RoutingSolution> pollSolved(@NotNull Job job) {
        CompletableFuture<RoutingSolution> solvedFuture = new CompletableFuture<>();
        Runnable pollSolved = getPollIfSolved(job, solvedFuture);
        int delay = Optional.ofNullable(job.getSolveDuration()).orElse(5) *1000;
        final ScheduledFuture<?> checkFuture = scheduler.scheduleWithFixedDelay(pollSolved, delay, SOLVED_POLL_PERIOD, TimeUnit.MILLISECONDS);
        return solvedFuture.whenComplete((result2, thrown2) -> checkFuture.cancel(false))
                .exceptionally(this::handleNotCompletedSolutionException);
    }

    private Runnable getPollIfSolved(@NotNull Job job, CompletableFuture<RoutingSolution> solvedFuture) {
        return () -> {
                if (job.getStatus() == Status.SOLVED) {
                    try {
                        RoutingSolution routingSolution = getRoutingSolution(job);
                        solvedCallback(routingSolution);
                        solvedFuture.complete(routingSolution);
                    } catch (ApiException e) {
                        log.error("Error finding status for {} and HTTP status {} and message: {}",
                                job.getId(),e.getCode(),e.getResponseBody());
                    }
                }
            };
    }

    /** Until the job starts solving, it is queued.
     * @param job the job that was sent to the solve endpoint
     * @param solvingFuture
     * @param solveId
     * @return has the process
     */
    private Runnable getPollIfSolving(@NotNull Job job, CompletableFuture<Job> solvingFuture, UUID solveId) {
        return () ->{
                Job infoJob = null;
                try {
                    assert solveId != null;
                    infoJob = jobsApi.getJobStatus(solveId.toString());
                } catch (ApiException e) {
                    log.error("Error finding status for {} and HTTP status {} and message: {}",
                            job.getId(),e.getCode(),e.getResponseBody());
                }
                if(infoJob.getStatus() == Status.ERROR ) {
                    handleErrorPoll(infoJob);
                    solvingFuture.complete(infoJob);
                }
                if(infoJob.getStatus() == Status.SOLVED || infoJob.getStatus() == Status.SOLVING){
                    solvingFuture.complete(infoJob);
                }
            };
    }


    protected abstract void solvedCallback(RoutingSolution routingSolution);

    protected abstract void handleErrorPoll(Job infoJob);

    protected RoutingSolution handleNotCompletedSolutionException(Throwable ex) {
        return null;
    }
    protected Job handleNotCompletedJobException(Throwable ex){
        return null;
    }

    private Job ERROR_JOB = new Job().status(Status.ERROR);
    private RoutingSolution ERROR_SOLUTION = new RoutingSolution();


    public OnRouteApi solvePollPeriod(int solvePollPeriod) {
        SOLVED_POLL_PERIOD = solvePollPeriod;
        return this;
    }

    public OnRouteApi queuePollPeriod(int queuePollPeriod) {
        QUEUE_POLL_PERIOD = queuePollPeriod;
        return this;
    }

}
