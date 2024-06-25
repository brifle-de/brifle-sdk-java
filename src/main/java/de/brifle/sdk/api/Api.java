package de.brifle.sdk.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.brifle.sdk.api.requests.ReceiverRequest;
import de.brifle.sdk.api.responses.authentication.SuccessfulAuthenticationResponse;
import de.brifle.sdk.api.responses.content.CheckReceiverResponse;

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

    private HttpRequest buildPostRequest(final String endpoint, final Map<String, Object> body) {
        final String json = toJson(body);

        return HttpRequest.newBuilder()
                .uri(URI.create(mode.getUrl() + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    public SuccessfulAuthenticationResponse authenticate(final String apiKey, final String apiSecret) throws IOException, InterruptedException {
        final String endpoint = "/v1/auth/login";
        final Map<String, Object> body = new HashMap<>();
        body.put("key", apiKey);
        body.put("secret", apiSecret);

        HttpRequest request = buildPostRequest(endpoint, body);

        final HttpResponse<String> res = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(res.body());
        SuccessfulAuthenticationResponse response = jsonConverter.readValue(res.body(), SuccessfulAuthenticationResponse.class);
        return response;

    }

    public CheckReceiverResponse checkReceiver(final ReceiverRequest request) {
        return null;
    }

}
