package io.solvice.onroute.internal;

import io.solvice.ApiException;
import io.solvice.ApiClient;
import io.solvice.Configuration;
import io.solvice.Pair;

import javax.ws.rs.core.GenericType;

import io.solvice.onroute.RoutingSolution;
import java.util.UUID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionApi {
    private ApiClient apiClient;

    public SolutionApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SolutionApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Solution
     * Returns the actual solution of the routing problem. Only present when the status is &#x60;SOLVED&#x60;.
     * @param jobId The job ID. (required)
     * @return a {@code RoutingSolution}
     * @throws ApiException if fails to make API call
     */
    public RoutingSolution getSolution(UUID jobId) throws ApiException {
        Object localVarPostBody = null;

        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException(400, "Missing the required parameter 'jobId' when calling getSolution");
        }

        // create path and map variables
        String localVarPath = "/jobs/{jobId}/solution".replaceAll("\\{format\\}","json")
                .replaceAll("\\{" + "jobId" + "\\}", apiClient.escapeString(jobId.toString()));

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();


        localVarHeaderParams.put("Content-Type","application/json");



        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        GenericType<RoutingSolution> localVarReturnType = new GenericType<RoutingSolution>() {};
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
