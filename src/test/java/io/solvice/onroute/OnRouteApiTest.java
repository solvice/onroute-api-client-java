package io.solvice.onroute;

import org.junit.Test;

import static org.junit.Assert.*;

public class OnRouteApiTest {

    @Test
    public void solve() {
        OnRouteApi api = new OnRouteApi();

        VRP vrp = new VRP();

        vrp.setSolver(SolveRequest.SolverEnum.VRP);
        vrp.addFleetItem(new Vehicle().startlocation("depot"));



    }
}
