package io.solvice.onroute;

import io.solvice.ApiClient;
import io.solvice.onroute.internal.RoutingApi;
import org.springframework.beans.factory.annotation.Autowired;

public class OnRouteApi {

    private final RoutingApi routingApi;

    public OnRouteApi() {
        this.routingApi = new RoutingApi();
    }

    @Autowired
    public OnRouteApi(ApiClient apiClient, RoutingApi routingApi) {
        this.routingApi = routingApi;
    }

    public void solve(SolveRequest request) {
         routingApi.solve(request);
    }


}
