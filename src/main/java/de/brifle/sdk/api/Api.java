package de.brifle.sdk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.brifle.sdk.api.endpoints.AuthEndpoint;
import de.brifle.sdk.api.endpoints.ContentEndpoint;
import de.brifle.sdk.api.endpoints.SignaturesEndpoint;
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

    private final SignaturesEndpoint signaturesEndpoint;
    private final ContentEndpoint contentEndpoint;

    private final AuthEndpoint authEndpoint;


    public Api(ApiMode mode) {
        this.mode = mode;
        this.client = HttpClient.newHttpClient();
        this.signaturesEndpoint = new SignaturesEndpoint(mode, client);
        this.contentEndpoint = new ContentEndpoint(mode, client);
        this.authEndpoint = new AuthEndpoint(mode, client);
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
     * Returns the auth endpoint
     * @return
     */
    public AuthEndpoint auth() {
        return authEndpoint;
    }



    /**
     * Returns the signatures endpoint
     * @return
     */
    public SignaturesEndpoint signatures() {
        return signaturesEndpoint;
    }

    /**
     * Returns the content endpoint
     * @return
     */
    public ContentEndpoint content() {
        return contentEndpoint;
    }

}
