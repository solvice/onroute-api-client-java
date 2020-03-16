package io.solvice.onroute;

import io.solvice.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnRouteApiImpl extends OnRouteApi {

    private final Logger log = LoggerFactory.getLogger(OnRouteApiImpl.class);

    public OnRouteApiImpl(ApiClient client) {
        super(client);
    }

    @Override
    protected void solvedCallback(RoutingSolution routingSolution) {
        log.info("Solved with score: {}", routingSolution.getScore());
    }

    @Override
    protected void handleErrorPoll(Job job) {
        log.error("Error solving job {}",job.getId());
    }

    @Override
    public Job handleNotCompletedException(Throwable ex) {
        return null;
    }
}
