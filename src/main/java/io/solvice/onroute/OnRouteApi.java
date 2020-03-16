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


    public OnRouteApi solvePollPeriod(int solvePollPeriod) {
        SOLVED_POLL_PERIOD = solvePollPeriod;
        return this;
    }

    public OnRouteApi queuePollPeriod(int queuePollPeriod) {
        QUEUE_POLL_PERIOD = queuePollPeriod;
        return this;
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

    public CompletableFuture<Job> pollSolution(@NotNull Job job) {
        CompletableFuture<Job> solvingFuture = new CompletableFuture<>();
        CompletableFuture<Job> solvedFuture = new CompletableFuture<>();
        final UUID solveId = job.getId();
        int initialDelay = Optional.ofNullable(job.getSolveDuration()).orElse(5);
        Runnable pollSolving = () ->{
            Job infoJob = null;
            try {
                assert solveId != null;
                infoJob = jobsApi.getJobStatus(solveId.toString());
                log.info(infoJob.toString());
            } catch (ApiException e) {
                log.error("Error finding status for {} and HTTP status {} and message: {}",job.getId(),e.getCode(),e.getResponseBody());
            }
            if(infoJob.getStatus() == Status.ERROR ) {
                handleErrorPoll(infoJob);
                solvingFuture.complete(infoJob);
            }
            if(infoJob.getStatus() == Status.SOLVED){
                solvingFuture.complete(infoJob);
            }
        };
        Runnable pollSolved = () -> {
            if (job.getStatus() == Status.SOLVED) {
                try {
                    RoutingSolution routingSolution = getRoutingSolution(job);
                    solvedCallback(routingSolution);
                } catch (ApiException e) {
                    log.error("Error finding status for {} and HTTP status {} and message: {}",job.getId(),e.getCode(),e.getResponseBody());
                }
            }
        };
        final ScheduledFuture<?> checkPolling = scheduler.scheduleWithFixedDelay(pollSolving, 0, QUEUE_POLL_PERIOD, TimeUnit.MILLISECONDS);
        return solvingFuture
                .whenComplete((result, thrown) ->  {
                    final ScheduledFuture<?> checkFuture = scheduler.scheduleWithFixedDelay(pollSolved, initialDelay*1000, SOLVED_POLL_PERIOD, TimeUnit.MILLISECONDS);
                    solvedFuture.whenComplete((result2, thrown2) ->  checkFuture.cancel(false))
                            .exceptionally(this::handleNotCompletedException);
                    checkPolling.cancel(false);
                })
                .exceptionally(this::handleNotCompletedException);
    }

    protected abstract void solvedCallback(RoutingSolution routingSolution);

    protected abstract void handleErrorPoll(Job infoJob);

    protected abstract Job handleNotCompletedException(Throwable ex);

}
