package de.brifle.sdk.api;

public enum ApiMode {

    SANDBOX("https://sandbox-api.brifle.de"), PRODUCTION("https://api.brifle.de");

    private String url;

    ApiMode(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
