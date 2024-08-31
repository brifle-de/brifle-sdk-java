package de.brifle.sdk.api.endpoints;

import de.brifle.sdk.api.ApiMode;
import de.brifle.sdk.api.requests.CreateSignatureReferenceRequest;
import de.brifle.sdk.api.responses.ApiResponse;
import de.brifle.sdk.api.responses.ErrorResponse;
import de.brifle.sdk.api.responses.signatures.CreateSignatureReferenceResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SignaturesEndpoint extends EndpointBase {


    public SignaturesEndpoint(ApiMode mode, HttpClient client) {
        super(mode, client);
    }

    /**
     * Create a new signature reference for a given tenant for requesting signatures
     * @param authToken
     * @param tenantId
     * @param request
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ApiResponse<CreateSignatureReferenceResponse> createSignatureReference(final String authToken,
                                                                                  final String tenantId,
                                                                                  final CreateSignatureReferenceRequest request) throws IOException, InterruptedException {
        String json = jsonConverter.writeValueAsString(request);
        HttpRequest httpRequest = buildPostRequest("/v1/signature/"+tenantId+"/reference", authToken, json);
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            ErrorResponse errorResponse = jsonConverter.readValue(response.body(), ErrorResponse.class);
            return ApiResponse.error(errorResponse);
        }
        return ApiResponse.success(jsonConverter.readValue(response.body(), CreateSignatureReferenceResponse.class));
    }



}
