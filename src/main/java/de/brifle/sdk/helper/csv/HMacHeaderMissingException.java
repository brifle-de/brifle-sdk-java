package de.brifle.sdk.helper.csv;

public class HMacHeaderMissingException extends Exception {
    public HMacHeaderMissingException() {
        super("The HMAC header is missing.");
    }
}
