package de.brifle.sdk.api.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class CheckReceiverRequestTest {

    @Test
    public void testSendByBirthinfo() throws JsonProcessingException {

        ReceiverRequest request = ReceiverRequest
                .byBirthInformation()
                .withFirstName("Max")
                .withLastName("Mustermann")
                .withDateOfBirth("1970-01-01")
                .withPlaceOfBirth("Musterstadt")
                .withNationality("GERMANY")
                .withNameAtBirth("OldName")
                .buildRequest();

        assert request != null;
        assert request.getAddressBook() == null;
        assert request.getBirthInformation() != null;
        assert request.getBirthInformation().getGivenNames().equals("Max");
        assert request.getBirthInformation().getLastName().equals("Mustermann");
        assert request.getBirthInformation().getDateOfBirth().equals("1970-01-01");
        assert request.getBirthInformation().getPlaceOfBirth().equals("Musterstadt");
        assert request.getBirthInformation().getNationality().equals("GERMANY");
        assert request.getBirthInformation().getBirthName().equals("OldName");
        assert request.getEmail() == null;
        assert request.getTel() == null;

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        assert json.length() > 10;
    }

    @Test
    public void testSendByAddressBook() throws JsonProcessingException {

        ReceiverRequest request = ReceiverRequest
                .byAddressBook()
                .withId("123456")
                .withOwner("AccountId")
                .buildRequest();

        assert request != null;
        assert request.getAddressBook() != null;
        assert request.getAddressBook().getOwner().equals("AccountId");
        assert request.getAddressBook().getId().equals("123456");
        assert request.getBirthInformation() == null;
        assert request.getEmail() == null;
        assert request.getTel() == null;

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        assert json.length() > 10;
    }

    @Test
    public void testSendByEmail() throws JsonProcessingException {

        ReceiverRequest request = ReceiverRequest
                .byEmail()
                .withEmail("kontakt@brifle.de")
                .withName("Max Mustermann")
                .withDateOfBirth("1970-01-01")
                .buildRequest();

        assert request != null;
        assert request.getEmail() != null;
        assert request.getEmail().equals("kontakt@brifle.de");
        assert request.getFullName().equals("Max Mustermann");
        assert request.getDateOfBirth().equals("1970-01-01");
        assert request.getBirthInformation() == null;
        assert request.getAddressBook() == null;
        assert request.getTel() == null;
    }

    @Test
    public void testSendByTel() throws JsonProcessingException {

        ReceiverRequest request = ReceiverRequest
                .byPhoneNumber()
                .withPhoneNumber("+123456")
                .withName("Max Mustermann")
                .withDateOfBirth("1970-01-01")
                .buildRequest();

        assert request != null;
        assert request.getTel() != null;
        assert request.getTel().equals("+123456");
        assert request.getFullName().equals("Max Mustermann");
        assert request.getDateOfBirth().equals("1970-01-01");
        assert request.getBirthInformation() == null;
        assert request.getAddressBook() == null;
        assert request.getEmail() == null;
    }



}