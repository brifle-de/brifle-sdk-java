package de.brifle.sdk.api.requests;

import java.util.ArrayList;
import java.util.List;

public class SendContentRequest {

    private String subject;
    private ReceiverRequest to;
    private List<Content> body;

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

}
