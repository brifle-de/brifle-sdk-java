package de.brifle.sdk;

import de.brifle.sdk.api.Api;
import de.brifle.sdk.api.ApiMode;
import de.brifle.sdk.api.responses.authentication.SuccessfulAuthenticationResponse;
import org.junit.jupiter.api.Test;

public class ApiTest {

    @Test
    public void testAuthenticate() {

        Api api = new Api(ApiMode.SANDBOX);

        // read the key and secret from the environment
         String secret = System.getProperty("BRIFLE_SECRET");
         String key = System.getProperty("BRIFLE_KEY");

        try {
            SuccessfulAuthenticationResponse response = api.authenticate(key, secret);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
