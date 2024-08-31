package de.brifle.sdk.api.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.brifle.sdk.api.ApiMode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Map;

public abstract class EndpointBase {

    protected final HttpClient client;

    protected final ObjectMapper jsonConverter = new ObjectMapper();

    protected final ApiMode mode;

    protected EndpointBase(ApiMode mode) {
        this.mode = mode;
        this.client = HttpClient.newHttpClient();
    }

    protected EndpointBase(ApiMode mode, HttpClient client) {
        this.mode = mode;
        this.client = client;
    }

    private String toJson(Object object) {
        try {
            return jsonConverter.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected HttpRequest buildPostRequest(final String endpoint, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(mode.getUrl() + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
    protected HttpRequest buildPostRequest(final String endpoint, String authToken, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(mode.getUrl() + endpoint))
                .header("Content-Type", "application/json")
                .headers("Authorization", "Bearer " + authToken)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    protected HttpRequest buildPostRequest(final String endpoint, final Map<String, Object> body) {
        final String json = toJson(body);
        return buildPostRequest(endpoint, json);
    }

    protected HttpRequest buildGetRequest(final String endpoint, String authToken) {
        return HttpRequest.newBuilder()
                .uri(URI.create(mode.getUrl() + endpoint))
                .header("Content-Type", "application/json")
                .headers("Authorization", "Bearer " + authToken)
                .GET()
                .build();
    }



}
