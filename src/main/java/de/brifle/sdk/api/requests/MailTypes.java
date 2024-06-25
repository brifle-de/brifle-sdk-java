package de.brifle.sdk.api.requests;

public enum MailTypes {

    LETTER("letter"), INVOICE("invoice"), CONTRACT("contract");

    private String type;
    private MailTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }



}
