package de.brifle.sdk.api.responses;

public class ErrorResponse {

    private int code;
    private String message;
    private int status;

    /**
     * gets the code
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * gets the status
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }


}
