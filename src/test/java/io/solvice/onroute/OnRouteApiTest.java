package io.solvice.onroute;


import io.solvice.ApiClient;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import static io.solvice.onroute.Status.SOLVED;
import static io.solvice.onroute.Status.SOLVING;


public class OnRouteApiTest {

    @Test

    public void solve()  {
        ApiClient client = new ApiClient();
        client.setUsername("demo");
        client.setPassword("demo");
        OnRouteApi api = new OnRouteApi(client);


        VRP vrp = new VRP();
        vrp.setSolver(Solver.VRP);
        vrp.addLocationsItem(new Location().name("depot").latitude(51.21112).longitude(4.093));
        vrp.addLocationsItem(new Location().name("l1").latitude(50.721112).longitude(4.893));
        vrp.addLocationsItem(new Location().name("l2").latitude(50.42342).longitude(4.693));


        vrp.addFleetItem(new Vehicle().name("bert").startlocation("depot")
                .shiftstart(500).shiftend(700)
                .addUnavailableItem(LocalDate.now())
        );
        vrp.addOrdersItem(new Order().location("l1").duration(10));
        vrp.addOrdersItem(new Order().name("o2").location("l2").duration(15));
        vrp.setOptions(new Options().traffic(1));

        // send to solver
        Job job = api.solve(vrp);

        // job has started solving
        Job startedSolving = null;
        try {
            startedSolving = api.pollSolving(job).get();
            Assert.assertTrue("Has Started solving",startedSolving.getStatus() == SOLVING || startedSolving.getStatus() == SOLVED);

            // job is solved. return solution

            RoutingSolution solution = (RoutingSolution) api.pollSolved(startedSolving, RoutingSolution.class).get();
            Assert.assertTrue("Solution is feasible with score: "+solution.getScore().getSoftScore(),solution.getScore().getFeasible());
            Assert.assertTrue("Solution is present: ",!solution.getSolution().isEmpty());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void solvePVRP() {
        ApiClient client = new ApiClient();
        client.setUsername("demo");
        client.setPassword("demo");
        OnRouteApi api = new OnRouteApi(client);

        PVRP vrp = new PVRP();
        vrp.setSolver(Solver.PVRP);
        vrp.setPeriod(new PVRPAllOfPeriod().start(LocalDate.now()).end(LocalDate.now().plusDays(4)));
        vrp.addLocationsItem(new Location().name("depot").latitude(51.21112).longitude(4.093));
        vrp.addLocationsItem(new Location().name("l1").latitude(50.721112).longitude(4.893));
        vrp.addLocationsItem(new Location().name("l2").latitude(50.42342).longitude(4.693));

        vrp.addFleetItem(new Vehicle().name("bert").startlocation("depot").shiftstart(500).shiftend(700));
        vrp.addOrdersItem(new Order().location("l1").duration(10));
        vrp.addOrdersItem(new Order().name("o2").location("l2").duration(15));
        vrp.setOptions(new Options().traffic(1));

        // send to solver
        Job job = api.solve(vrp);

        // job has started solving
        Job startedSolving = null;
        try {
            startedSolving = api.pollSolving(job).get();
            Assert.assertTrue("Has Started solving",startedSolving.getStatus() == SOLVING || startedSolving.getStatus() == SOLVED);

            // job is solved. return solution

            PeriodicRoutingSolution solution = (PeriodicRoutingSolution) api.pollSolved(startedSolving, PeriodicRoutingSolution.class).get();
            Assert.assertTrue("Solution is feasible with score: "+solution.getScore().getSoftScore(),solution.getScore().getFeasible());
            Assert.assertTrue("Solution is present: ",!solution.getSolution().isEmpty());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }




    }
}

