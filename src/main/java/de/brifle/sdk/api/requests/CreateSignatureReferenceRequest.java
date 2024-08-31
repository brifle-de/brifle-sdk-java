package de.brifle.sdk.api.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSignatureReferenceRequest {
    @JsonProperty("fields")
    private List<Field> fields;

    protected CreateSignatureReferenceRequest() {
    }

    protected CreateSignatureReferenceRequest(List<Field> fields) {
        this.fields = fields;
    }

    private CreateSignatureReferenceRequest(Builder builder) {
        fields = builder.fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public static Builder builder() {
        return new Builder();
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Field {
        @JsonProperty("name")
        private String name;
        @JsonProperty("role")
        private String role;
        @JsonProperty("purpose")
        private String purpose;

        protected Field() {
        }

        public Field(String name, String role, String purpose) {
            this.name = name;
            this.role = role;
            this.purpose = purpose;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public String getPurpose() {
            return purpose;
        }
    }

    public static final class Builder {
        private List<Field> fields = new LinkedList<>();

        private Builder() {
        }

        public Builder addField(String name, String role, String purpose) {
            fields.add(new Field(name, role, purpose));
            return this;
        }
        public Builder addField(Field val) {
            fields.add(val);
            return this;
        }

        public CreateSignatureReferenceRequest build() {
            return new CreateSignatureReferenceRequest(this);
        }
    }
}
