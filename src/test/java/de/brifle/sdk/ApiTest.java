package de.brifle.sdk;

import de.brifle.sdk.api.Api;
import de.brifle.sdk.api.ApiMode;
import de.brifle.sdk.api.requests.MailTypes;
import de.brifle.sdk.api.requests.ReceiverRequest;
import de.brifle.sdk.api.requests.SendContentRequest;
import de.brifle.sdk.api.responses.ApiResponse;
import de.brifle.sdk.api.responses.authentication.SuccessfulAuthenticationResponse;
import de.brifle.sdk.api.responses.content.CheckReceiverResponse;
import de.brifle.sdk.api.responses.content.SendContentResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class ApiTest {


    private String getToken(){
        // read the key and secret from the environment
        String secret = System.getProperty("BRIFLE_SECRET");
        String key = System.getProperty("BRIFLE_KEY");
        Api api = new Api(ApiMode.SANDBOX);
        try {
            SuccessfulAuthenticationResponse response = api.authenticate(key, secret).getData();
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
            SuccessfulAuthenticationResponse response = api.authenticate(key, secret).getData();
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

            CheckReceiverResponse re = api.checkReceiver(token, request).getData();


            ReceiverRequest request2 = ReceiverRequest
                    .byBirthInformation()
                    .withFirstName(System.getProperty("BRIFLE_EXISTING_USER_2_FIRSTNAME"))
                    .withLastName(System.getProperty("BRIFLE_EXISTING_USER_2_LASTNAME"))
                    .withDateOfBirth(System.getProperty("BRIFLE_EXISTING_USER_2_DATE_OF_BIRTH"))
                    .withPlaceOfBirth(System.getProperty("BRIFLE_EXISTING_USER_2_PLACE_OF_BIRTH"))
                    .buildRequest();

            CheckReceiverResponse re2 = api.checkReceiver(token, request2).getData();

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

        ApiResponse<CheckReceiverResponse> re = api.checkReceiver(token, request);
        assert re.isError();
        assert !re.isSuccess();
        assert re.getData() == null;
        assert re.getError() != null;

        assert re.getError().getCode() == 40401;
        assert re.getError().getStatus() == 404;




    }

    @Test
    public void testSend() throws IOException, InterruptedException {
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

        SendContentResponse res = api.sendContent(token, tenant, request).getData();
        assert content != null;
    }
}
