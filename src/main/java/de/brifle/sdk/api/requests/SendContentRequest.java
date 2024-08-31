package de.brifle.sdk.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SendContentRequest {

    private String subject;
    private ReceiverRequest to;
    private List<Content> body;

    @JsonProperty("payment_info")
    private PaymentInfo paymentInfo;

    @JsonProperty("signature_info")
    private SignatureInfo signatureInfo;


    private String type;

    private SendContentRequest(){

    }

    public String getSubject() {
        return subject;
    }

    public ReceiverRequest getTo() {
        return to;
    }

    public List<Content> getBody() {
        return body;
    }

    public String getType() {
        return type;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public SignatureInfo getSignatureInfo() {
        return signatureInfo;
    }


    public static Builder builder() {
        return new Builder();
    }

    // add builder

    public static class Builder {
        private SendContentRequest request = new SendContentRequest();

        public Builder withSubject(String subject) {
            request.subject = subject;
            return this;
        }

        public Builder withTo(ReceiverRequest to) {
            request.to = to;
            return this;
        }

        public Builder withBody(List<Content> body) {
            request.body = body;
            return this;
        }

        public Builder addPdfToBody(String base64EncodedPdf) {
            if(request.body == null) {
                request.body = new ArrayList<>();
            }
            request.body.add(Content.pdf(base64EncodedPdf));
            return this;
        }

        public Builder withType(MailTypes type) {
            request.type = type.getType();
            return this;
        }

        public Builder withPaymentInfo(PaymentInfo paymentInfo) {
            request.paymentInfo = paymentInfo;
            return this;
        }

        public Builder withSignatureInfo(SignatureInfo signatureInfo) {
            request.signatureInfo = signatureInfo;
            return this;
        }

        public SendContentRequest build() {
            return request;
        }
    }


    public static class Content {
        // Meme type of the content, e.g. "application/pdf"
        private String type;
        // This is a base64 encoded string
        private String content;

        public String getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public static Content pdf(String base64EncodedPdf) {
            Content content = new Content();
            content.type = "application/pdf";
            content.content = base64EncodedPdf;
            return content;
        }
    }

    public static class PaymentInfo {


        private PaymentDetails details;

        private boolean payable;

        public PaymentDetails getDetails() {
            return details;
        }

        public boolean isPayable() {
            return payable;
        }

        public static class PaymentDetails {
            private int amount;
            private String currency;
            private String description;
            @JsonProperty("due_date")
            private String dueDate;
            private String iban;
            private String reference;

            protected PaymentDetails() {
            }

            public int getAmount() {
                return amount;
            }

            public String getCurrency() {
                return currency;
            }

            public String getDescription() {
                return description;
            }

            public String getDueDate() {
                return dueDate;
            }

            public String getIban() {
                return iban;
            }

            public String getReference() {
                return reference;
            }
        }




    }

    public static class SignatureInfo{


        @JsonProperty("requesting_signer")
        private List<RequestingSigner> requestingSigner = new LinkedList<>();

        @JsonProperty("signature_reference")
        private String signatureReference;

        protected SignatureInfo() {
        }

        private SignatureInfo(Builder builder) {
            requestingSigner = builder.requestingSigner;
            signatureReference = builder.signatureReference;
        }

        public List<RequestingSigner> getRequestingSigner() {
            return requestingSigner;
        }

        public String getSignatureReference() {
            return signatureReference;
        }

        public static Builder builder() {
            return new Builder();
        }


        public static final class Builder {
            private List<RequestingSigner> requestingSigner;
            private String signatureReference;

            protected Builder() {
            }

            public Builder withRequestingSigner(List<RequestingSigner> val) {
                requestingSigner = val;
                return this;
            }

            public Builder withSignatureReference(String val) {
                signatureReference = val;
                return this;
            }

            private Builder addSignatureReference(String field, String signer) {
                if(requestingSigner == null) {
                    requestingSigner = new ArrayList<>();
                }
                RequestingSigner rs = new RequestingSigner();
                rs.field = field;
                rs.signer = signer;
                requestingSigner.add(rs);
                return this;
            }

            public Builder addSenderSignature(String field) {
                return addSignatureReference(field, "sender");
            }

            public Builder addReceiverSignature(String field) {
                return addSignatureReference(field, "receiver");
            }

            public SignatureInfo build() {
                return new SignatureInfo(this);
            }
        }
    }

    public static class RequestingSigner {
        private String field;
        private String signer;

        protected RequestingSigner() {
        }

        public String getField() {
            return field;
        }

        public String getSigner() {
            return signer;
        }
    }

}
