package io.solvice.onroute;


import io.solvice.ApiClient;
import io.solvice.ApiException;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class OnRouteApiTest2 {

    @Test
    public void solve() throws InterruptedException {
        ApiClient client = new ApiClient();
        client.setUsername("onroute");
        client.setPassword("onroute");
        OnRouteApi api = new OnRouteApiImpl(client);

        VRP vrp = new VRP();
        vrp.setSolver(Solver.VRP);
        vrp.addLocationsItem(new Location().name("depot").latitude(51.21112).longitude(4.093));
        vrp.addLocationsItem(new Location().name("l1").latitude(50.721112).longitude(4.893));
        vrp.addLocationsItem(new Location().name("l2").latitude(50.42342).longitude(4.693));
        vrp.addFleetItem(new Vehicle().name("bert").startlocation("depot").shiftstart(500).shiftend(700));
        vrp.addOrdersItem(new Order().name("o1").location("l1").duration(10));
        vrp.addOrdersItem(new Order().name("o2").location("l2").duration(15));
        vrp.setOptions(new Options().traffic(1));

        Job job = api.solve(vrp);


        CompletableFuture<Job> jobCompletableFuture = api.pollSolution(job);
        Thread.sleep(10000);

        jobCompletableFuture.whenComplete((j,d) -> {
            System.out.println(j.getStatus());
        });


    }
}

