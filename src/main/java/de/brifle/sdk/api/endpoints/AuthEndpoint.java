package de.brifle.sdk.api.endpoints;

import de.brifle.sdk.api.ApiMode;
import de.brifle.sdk.api.requests.CreateSignatureReferenceRequest;
import de.brifle.sdk.api.responses.ApiResponse;
import de.brifle.sdk.api.responses.ErrorResponse;
import de.brifle.sdk.api.responses.authentication.SuccessfulAuthenticationResponse;
import de.brifle.sdk.api.responses.signatures.CreateSignatureReferenceResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class AuthEndpoint extends EndpointBase {


    public AuthEndpoint(ApiMode mode, HttpClient client) {
        super(mode, client);
    }

    /**
     * Authenticates with the API. It contains a token and the expiration information
     * @param apiKey the api key
     * @param apiSecret the api secret
     * @return the response
     * @throws IOException if an error occurs
     * @throws InterruptedException if an error occurs
     */
    public ApiResponse<SuccessfulAuthenticationResponse> authenticate(final String apiKey, final String apiSecret) throws IOException, InterruptedException {
        final String endpoint = "/v1/auth/login";
        final Map<String, Object> body = new HashMap<>();
        body.put("key", apiKey);
        body.put("secret", apiSecret);

        HttpRequest request = buildPostRequest(endpoint, body);

        final HttpResponse<String> res = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) {
            ErrorResponse errorResponse = jsonConverter.readValue(res.body(), ErrorResponse.class);
            return ApiResponse.error(errorResponse);
        }else{
            SuccessfulAuthenticationResponse response = jsonConverter.readValue(res.body(), SuccessfulAuthenticationResponse.class);
            return ApiResponse.success(response);
        }

    }



}
