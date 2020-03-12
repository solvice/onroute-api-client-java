package io.solvice.onroute.internal;

import io.solvice.ApiClient;
import io.solvice.onroute.Job;
import io.solvice.onroute.SolveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-03-12T16:58:10.354+01:00[Europe/Brussels]")
public class RoutingApi {
    private ApiClient apiClient;

    public RoutingApi() {
        this(new ApiClient());
    }

    @Autowired
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
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request.
     * @param UNKNOWN_BASE_TYPE Problem solve request
     * @return AnyOfVRPPVRPPDP
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Mono<SolveRequest> evaluate(SolveRequest request) throws RestClientException {
        Object postBody = request;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<SolveRequest> localVarReturnType = new ParameterizedTypeReference<SolveRequest>() {};
        return apiClient.invokeAPI("/evaluate", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Solve
     * Solves any problem defined underneath. Result is the job id and its status. Fetch the solution afterwards in the Solution endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request.
     * @param SolveRequest Problem solve request
     * @return Job
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Mono<Job> solve(SolveRequest request) throws RestClientException {
        Object postBody = request;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<Job> localVarReturnType = new ParameterizedTypeReference<Job>() {};
        return apiClient.invokeAPI("/solve", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }




}
