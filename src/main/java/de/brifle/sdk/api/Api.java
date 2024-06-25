package de.brifle.sdk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.brifle.sdk.api.requests.ReceiverRequest;
import de.brifle.sdk.api.requests.SendContentRequest;
import de.brifle.sdk.api.responses.ApiResponse;
import de.brifle.sdk.api.responses.ErrorResponse;
import de.brifle.sdk.api.responses.authentication.SuccessfulAuthenticationResponse;
import de.brifle.sdk.api.responses.content.CheckReceiverResponse;
import de.brifle.sdk.api.responses.content.SendContentResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class Api {

    private ApiMode mode;
    private HttpClient client;

    private ObjectMapper jsonConverter = new ObjectMapper();

    public Api(ApiMode mode) {
        this.mode = mode;
        this.client = HttpClient.newHttpClient();
    }

    private String toJson(Object object) {
        try {
            return jsonConverter.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private HttpRequest buildPostRequest(final String endpoint, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(mode.getUrl() + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
    private HttpRequest buildPostRequest(final String endpoint, String authToken, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(mode.getUrl() + endpoint))
                .header("Content-Type", "application/json")
                .headers("Authorization", "Bearer " + authToken)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
    private HttpRequest buildPostRequest(final String endpoint, final Map<String, Object> body) {
        final String json = toJson(body);
        return buildPostRequest(endpoint, json);
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

    /**
     * Checks if a receiver is valid
     * @param authToken the auth token generated by the authenticate method
     * @param request the request
     * @return the response
     * @throws IOException if an error occurs
     * @throws InterruptedException if an error occurs
     */
    public ApiResponse<CheckReceiverResponse> checkReceiver(final String authToken, final ReceiverRequest request) throws IOException, InterruptedException {
        String json = jsonConverter.writeValueAsString(request);
        HttpRequest httpRequest = buildPostRequest("/v1/content/receiver/check", authToken, json);
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            ErrorResponse errorResponse = jsonConverter.readValue(response.body(), ErrorResponse.class);
            return ApiResponse.error(errorResponse);
        }
        return ApiResponse.success(jsonConverter.readValue(response.body(), CheckReceiverResponse.class));
    }


    /**
     * Sends content to a receiver
     * @param authToken the auth token generated by the authenticate method
     * @param tenant the tenant id of the sender
     * @param request the request
     * @return the response
     * @throws IOException if an error occurs
     * @throws InterruptedException if an error occurs
     */
    public ApiResponse<SendContentResponse> sendContent(final String authToken, final String tenant, final SendContentRequest request) throws IOException, InterruptedException {
        String json = jsonConverter.writeValueAsString(request);
        HttpRequest httpRequest = buildPostRequest("/v1/content/send/"+tenant, authToken, json);

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            ErrorResponse errorResponse = jsonConverter.readValue(response.body(), ErrorResponse.class);
            return ApiResponse.error(errorResponse);
        }
        return ApiResponse.success(jsonConverter.readValue(response.body(), SendContentResponse.class));
    }

}
