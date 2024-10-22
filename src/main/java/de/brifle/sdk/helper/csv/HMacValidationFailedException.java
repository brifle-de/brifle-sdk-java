package de.brifle.sdk.helper.csv;

public class HMacValidationFailedException  extends Exception{
    public HMacValidationFailedException() {
        super("The HMAC validation failed.");
    }
}
