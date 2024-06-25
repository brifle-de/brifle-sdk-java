package de.brifle.sdk.api.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class CheckReceiverRequestTest {

    @Test
    public void testSendByBirthinfo() throws JsonProcessingException {

        ReceiverRequest request = ReceiverRequest
                .byBirthInformation()
                .withFirstName("Max")
                .withLastName("Mustermann")
                .withDateOfBirth("01.01.1970")
                .withPlaceOfBirth("Musterstadt")
                .withNationality("GERMANY")
                .buildRequest();

        ObjectMapper mapper = new ObjectMapper();

        String r = mapper.writeValueAsString(request);

    }

}
