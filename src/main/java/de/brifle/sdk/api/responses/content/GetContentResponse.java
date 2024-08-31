package de.brifle.sdk.api.responses.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetContentResponse {

    private Meta meta;
    private List<Content> content;

    protected GetContentResponse() {
    }

    public Meta getMeta() {
        return meta;
    }

    public List<Content> getContent() {
        return content;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {

        private String id;

        private boolean delivered;
        @JsonProperty("delivered_date")
        private String deliveredDate;
        private boolean read;
        @JsonProperty("read_date")
        private String readDate;
        private String receiver;
        private String sender;
        private String subject;
        private String type;

        @JsonProperty("access_level")
        private int accessLevel;

        @JsonProperty("sender_state")
        private String senderState;
        @JsonProperty("receiver_state")
        private String receiverState;

        @JsonProperty("signature_reference")
        private String signatureReference;

        private int size;

        private boolean payable;



        protected Meta() {
        }

        public boolean isDelivered() {
            return delivered;
        }

        public String getDeliveredDate() {
            return deliveredDate;
        }

        public boolean isRead() {
            return read;
        }

        public String getReadDate() {
            return readDate;
        }

        public String getReceiver() {
            return receiver;
        }

        public String getSender() {
            return sender;
        }

        public String getSubject() {
            return subject;
        }

        public String getType() {
            return type;
        }

        public String getSenderState() {
            return senderState;
        }

        public String getReceiverState() {
            return receiverState;
        }

        public String getSignatureReference() {
            return signatureReference;
        }

        public int getSize() {
            return size;
        }

        public boolean isPayable() {
            return payable;
        }

        public int getAccessLevel() {
            return accessLevel;
        }

        /**
         * gets the id
         *
         * @return the id
         */
        public String getId() {
            return id;
        }
    }

    public static class Content {
        private String content;
        @JsonProperty("type")
        private String contentType;

        public String getContent() {
            return content;
        }

        public String getContentType() {
            return contentType;
        }
    }

}

