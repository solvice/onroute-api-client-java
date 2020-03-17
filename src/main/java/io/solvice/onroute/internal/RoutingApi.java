package io.solvice.onroute.internal;

import io.solvice.ApiException;
import io.solvice.ApiClient;
import io.solvice.Configuration;
import io.solvice.Pair;
import io.solvice.onroute.Job;
import io.solvice.onroute.SolveRequest;

import javax.ws.rs.core.GenericType;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutingApi {
    private ApiClient apiClient;

    public RoutingApi() {
        this(Configuration.getDefaultApiClient());
    }

    public RoutingApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Evaluate
     * Evaluates any problem defined underneath. Result is the job id and its status. Fetch the solution immediately in the response.
     * @param request Problem solve request (optional)
     * @return a {@code SolveRequest}
     * @throws ApiException if fails to make API call
     */
    public SolveRequest evaluate(SolveRequest request) throws ApiException {
        SolveRequest localVarPostBody = request;

        // create path and map variables
        String localVarPath = "/evaluate".replaceAll("\\{format\\}","json");

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();





        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        GenericType<SolveRequest> localVarReturnType = new GenericType<SolveRequest>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Solve
     * Solves any problem defined underneath. Result is the job id and its status. Fetch the solution afterwards in the Solution endpoint.
     * @param req Problem solve request (optional)
     * @return a {@code Job}
     * @throws ApiException if fails to make API call
     */
    public Job solve(SolveRequest req) throws ApiException {
        SolveRequest localVarPostBody = req;

        // create path and map variables
        String localVarPath = "/solve".replaceAll("\\{format\\}","json");

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();





        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        GenericType<Job> localVarReturnType = new GenericType<Job>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
