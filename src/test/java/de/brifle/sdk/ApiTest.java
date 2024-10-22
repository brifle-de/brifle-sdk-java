package de.brifle.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.brifle.sdk.api.Api;
import de.brifle.sdk.api.ApiMode;
import de.brifle.sdk.api.requests.CreateSignatureReferenceRequest;
import de.brifle.sdk.api.requests.MailTypes;
import de.brifle.sdk.api.requests.ReceiverRequest;
import de.brifle.sdk.api.requests.SendContentRequest;
import de.brifle.sdk.api.responses.ApiResponse;
import de.brifle.sdk.api.responses.authentication.SuccessfulAuthenticationResponse;
import de.brifle.sdk.api.responses.content.CheckReceiverResponse;
import de.brifle.sdk.api.responses.content.GetContentResponse;
import de.brifle.sdk.api.responses.content.SendContentResponse;
import de.brifle.sdk.api.responses.signatures.CreateSignatureReferenceResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ApiTest {


    private String getToken(){
        // read the key and secret from the environment
        String secret = System.getProperty("BRIFLE_SECRET");
        String key = System.getProperty("BRIFLE_KEY");
        Api api = new Api(ApiMode.SANDBOX);
        try {
            SuccessfulAuthenticationResponse response = api
                    .auth()
                    .authenticate(key, secret)
                    .getData();
            return response.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testAuthenticate() {

        Api api = new Api(ApiMode.SANDBOX);

        // read the key and secret from the environment
         String secret = System.getProperty("BRIFLE_SECRET");
         String key = System.getProperty("BRIFLE_KEY");

        try {
            SuccessfulAuthenticationResponse response = api
                    .auth()
                    .authenticate(key, secret)
                    .getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckReceiver() {
        Api api = new Api(ApiMode.SANDBOX);

        // read the key and secret from the environment
         String token = getToken();


        try {
            ReceiverRequest request = ReceiverRequest
                    .byEmail()
                    .withEmail(System.getProperty("BRIFLE_EXISTING_USER_EMAIL"))
                    .withDateOfBirth(System.getProperty("BRIFLE_EXISTING_USER_DATE_OF_BIRTH"))
                    .withName(System.getProperty("BRIFLE_EXISTING_USER_FULLNAME"))
                    .buildRequest();

            CheckReceiverResponse re = api.content().checkReceiver(token, request).getData();


            ReceiverRequest request2 = ReceiverRequest
                    .byBirthInformation()
                    .withFirstName(System.getProperty("BRIFLE_EXISTING_USER_2_FIRSTNAME"))
                    .withLastName(System.getProperty("BRIFLE_EXISTING_USER_2_LASTNAME"))
                    .withDateOfBirth(System.getProperty("BRIFLE_EXISTING_USER_2_DATE_OF_BIRTH"))
                    .withPlaceOfBirth(System.getProperty("BRIFLE_EXISTING_USER_2_PLACE_OF_BIRTH"))
                    .buildRequest();

            CheckReceiverResponse re2 = api.content().checkReceiver(token, request2).getData();

            assert re.getReceiver().getType().equals("email");
            assert re2.getReceiver().getType().equals("birth_info");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReceiverNotExisting() throws IOException, InterruptedException {
        Api api = new Api(ApiMode.SANDBOX);

        // read the key and secret from the environment
        String token = getToken();


        ReceiverRequest request = ReceiverRequest
                .byEmail()
                .withEmail("email_not_exist@brifle.de")
                .withDateOfBirth("1970-01-01")
                .withName("Max Mustermann")
                .buildRequest();

        ApiResponse<CheckReceiverResponse> re = api.content().checkReceiver(token, request);
        assert re.isError();
        assert !re.isSuccess();
        assert re.getData() == null;
        assert re.getError() != null;

        assert re.getError().getCode() == 40401;
        assert re.getError().getStatus() == 404;

    }

    @Test
    public void testSendAndGet() throws IOException, InterruptedException {
        // read from resources
        InputStream contentStream = this.getClass().getClassLoader().getResourceAsStream("Willkommensbrief-4.pdf");
        byte[] content = contentStream.readAllBytes();
        // to base64
        String base64 = java.util.Base64.getEncoder().encodeToString(content);

        String token = getToken();

        Api api = new Api(ApiMode.SANDBOX);

        ReceiverRequest to = ReceiverRequest
                .byEmail()
                .withEmail(System.getProperty("BRIFLE_EXISTING_USER_EMAIL"))
                .withDateOfBirth(System.getProperty("BRIFLE_EXISTING_USER_DATE_OF_BIRTH"))
                .withName(System.getProperty("BRIFLE_EXISTING_USER_FULLNAME"))
                .buildRequest();


        String tenant = System.getProperty("BRIFLE_TEST_TENANT");
        SendContentRequest request = SendContentRequest.builder()
                .withSubject("Willkommensbrief")
                .withTo(to)
                .addPdfToBody(base64)
                .withType(MailTypes.LETTER)
                .build();

        ApiResponse<SendContentResponse> res = api.content().sendContent(token, tenant, request);
        String id = res.getData().getId();

        ApiResponse<GetContentResponse> getRes = api
                .content()
                .getContent(token,id);

        assert getRes.getData() != null;
        assert getRes.getData().getContent() != null;
        assert getRes.getData().getContent().size() == 1;
        assert getRes.getData().getMeta() != null;
        assert getRes.getData().getMeta().getSender().equals(tenant);
        assert getRes.getData().getMeta().getSubject().equals("Willkommensbrief");
        assert getRes.getData().getMeta().getType().equals("letter");



        assert content != null;
    }

    @Test
    public void testContract() throws IOException, InterruptedException {
        // read from resources
        InputStream contentStream = this.getClass().getClassLoader().getResourceAsStream("Willkommensbrief-4.pdf");
        byte[] content = contentStream.readAllBytes();
        // to base64
        String base64 = java.util.Base64.getEncoder().encodeToString(content);

        String token = getToken();

        Api api = new Api(ApiMode.SANDBOX);

        ReceiverRequest to = ReceiverRequest
                .byEmail()
                .withEmail(System.getProperty("BRIFLE_EXISTING_USER_EMAIL"))
                .withDateOfBirth(System.getProperty("BRIFLE_EXISTING_USER_DATE_OF_BIRTH"))
                .withName(System.getProperty("BRIFLE_EXISTING_USER_FULLNAME"))
                .buildRequest();

        // request signature reference
        CreateSignatureReferenceRequest request = CreateSignatureReferenceRequest
                .builder()
                .addField("FeldName", "FeldRolle","Der Zweck")
                .addField("FeldName2", "FeldRolle2","Der Zweck2")
                .build();

        ApiResponse<CreateSignatureReferenceResponse> res = api
                .signatures()
                .createSignatureReference(token, System.getProperty("BRIFLE_TEST_TENANT"), request);

        // send content
        String tenant = System.getProperty("BRIFLE_TEST_TENANT");

        SendContentRequest.SignatureInfo signatureInfo = SendContentRequest.SignatureInfo
                .builder()
                .withSignatureReference(res.getData().getId())
                .addSenderSignature("FeldName")
                .addReceiverSignature("FeldName2")
                .build();

        SendContentRequest request2 = SendContentRequest.builder()
                .withSubject("Testvertrag")
                .withTo(to)
                .addPdfToBody(base64)
                .withType(MailTypes.CONTRACT)
                .withSignatureInfo(signatureInfo)
                .build();


        SendContentResponse res3 = api.content().sendContent(token, tenant, request2).getData();
        String id = res3.getId();




        assert content != null;

    }

    @Test
    public void requestSignatureReference() throws IOException, InterruptedException {
        String token = getToken();
        Api api = new Api(ApiMode.SANDBOX);

        CreateSignatureReferenceRequest request = CreateSignatureReferenceRequest
                .builder()
                .addField("FeldName", "FeldRolle","Der Zweck")
                .addField("FeldName2", "FeldRolle2","Der Zweck2")
                .build();

        assert request.getFields().size() == 2;
        assert request.getFields().get(0).getName().equals("FeldName");
        assert request.getFields().get(0).getRole().equals("FeldRolle");
        assert request.getFields().get(0).getPurpose().equals("Der Zweck");

        assert request.getFields().get(1).getName().equals("FeldName2");
        assert request.getFields().get(1).getRole().equals("FeldRolle2");
        assert request.getFields().get(1).getPurpose().equals("Der Zweck2");

         ApiResponse<CreateSignatureReferenceResponse> res = api
                .signatures()
                .createSignatureReference(token, System.getProperty("BRIFLE_TEST_TENANT"), request);

        assert res.getData() != null;

        String jsonFields = res.getData().getSignatureFields();
        List<CreateSignatureReferenceRequest.Field> fields = new ObjectMapper()
                .readValue(jsonFields, List.class)
                .stream().map(o -> new ObjectMapper().convertValue(o, CreateSignatureReferenceRequest.Field.class))
                .toList();

        assert fields.size() == 2;
        assert fields.get(0).getName().equals("FeldName");
        assert fields.get(0).getRole().equals("FeldRolle");
        assert fields.get(0).getPurpose().equals("Der Zweck");

        assert fields.get(1).getName().equals("FeldName2");
        assert fields.get(1).getRole().equals("FeldRolle2");
        assert fields.get(1).getPurpose().equals("Der Zweck2");

        assert res.getData().getId().length() > 10;

    }

}
