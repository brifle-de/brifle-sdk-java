package de.brifle.sdk.api.responses.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessfulAuthenticationResponse {


    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("token_type")
    private String tokenType;


    public String getAccessToken() {
        return accessToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

}
