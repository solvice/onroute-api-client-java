package io.solvice.onroute.internal;

import io.solvice.ApiClient;
import io.solvice.ApiException;
import io.solvice.Configuration;
import io.solvice.Pair;
import io.solvice.onroute.Job;
import io.solvice.onroute.Stats;

import javax.ws.rs.core.GenericType;
import java.util.*;

public class JobsApi {
    private ApiClient apiClient;

    public JobsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public JobsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Job
     * When posting a new solve request, this job can be checked again under this endpoint. In fact, it should be the entire request posted as-is.
     * @param jobId The job ID. (required)
     * @return a {@code Job}
     * @throws ApiException if fails to make API call
     */
    public Job getJob(UUID jobId) throws ApiException {
        Object localVarPostBody = null;

        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException(400, "Missing the required parameter 'jobId' when calling getJob");
        }

        // create path and map variables
        String localVarPath = "/jobs/{jobId}".replaceAll("\\{format\\}","json")
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
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        GenericType<Job> localVarReturnType = new GenericType<Job>() {};
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Status
     * Retrieve a specific job status
     * @param jobId The job ID. (required)
     * @return a {@code Job}
     * @throws ApiException if fails to make API call
     */
    public Job getJobStatus(String jobId) throws ApiException {
        Object localVarPostBody = null;

        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException(400, "Missing the required parameter 'jobId' when calling getJobStatus");
        }

        // create path and map variables
        String localVarPath = "/jobs/{jobId}/status".replaceAll("\\{format\\}","json")
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
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        GenericType<Job> localVarReturnType = new GenericType<Job>() {};
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Statistics
     * Returns the information on why a job has been solved the way it&#39;s been. Information includes specific unresolved objects. Want to know how it is optimised? This endpoint lets you know what rules have been overruled.
     * @param jobId The job ID. (required)
     * @return a {@code Stats}
     * @throws ApiException if fails to make API call
     */
    public Stats getStats(UUID jobId) throws ApiException {
        Object localVarPostBody = null;

        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException(400, "Missing the required parameter 'jobId' when calling getStats");
        }

        // create path and map variables
        String localVarPath = "/v1/stats/{jobId}".replaceAll("\\{format\\}","json")
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

        GenericType<Stats> localVarReturnType = new GenericType<Stats>() {};
        return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
