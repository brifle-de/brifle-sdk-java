package de.brifle.sdk.api.responses.content;

public class CheckReceiverResponse {

    Receiver receiver;

    public Receiver getReceiver() {
        return receiver;
    }

    public class Receiver {
        private String type;

        public String getType() {
            return type;
        }
    }
}
