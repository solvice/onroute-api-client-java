package io.solvice.onroute.internal;

import io.solvice.ApiClient;
import io.solvice.onroute.RoutingSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-03-12T16:58:10.354+01:00[Europe/Brussels]")
public class SolutionApi {
    private ApiClient apiClient;

    public SolutionApi() {
        this(new ApiClient());
    }

    @Autowired
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
     * <p><b>200</b> - OK
     * <p><b>401</b> - Job not found
     * @param jobId The job ID.
     * @return RoutingSolution
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Mono<RoutingSolution> getSolution(UUID jobId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'jobId' when calling getSolution");
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("jobId", jobId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<RoutingSolution> localVarReturnType = new ParameterizedTypeReference<RoutingSolution>() {};
        return apiClient.invokeAPI("/jobs/{jobId}/solution", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
