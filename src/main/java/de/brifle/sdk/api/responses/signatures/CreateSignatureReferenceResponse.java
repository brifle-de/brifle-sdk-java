package de.brifle.sdk.api.responses.signatures;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.brifle.sdk.api.requests.CreateSignatureReferenceRequest;

import java.util.List;

public class CreateSignatureReferenceResponse {

    protected String id;

    @JsonProperty("signature_fields")
    protected String signatureFields;

    @JsonProperty("document_signatures")
    protected String documentSignatures;

    @JsonProperty("managed_by")
    protected String managedBy;

    public String getId() {
        return id;
    }

    public String getSignatureFields() {
        return signatureFields;
    }

    public String getDocumentSignatures() {
        return documentSignatures;
    }

    public String getManagedBy() {
        return managedBy;
    }








}
