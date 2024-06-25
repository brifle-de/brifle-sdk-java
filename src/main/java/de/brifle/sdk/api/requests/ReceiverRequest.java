package de.brifle.sdk.api.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiverRequest {

    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("tel")
    private String tel;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    @JsonProperty("vat_id")
    private String vatId;
    @JsonProperty("ssn")
    private String ssn;
    @JsonProperty("birth_information")
    private ReceiverBirthInformation birthInformation;
    @JsonProperty("address_book")
    private ReceiverAddressBook addressBook;

    private ReceiverRequest() {
    }

    public static EmailBuilder byEmail() {
        return new ReceiverRequest.EmailBuilder();
    }

    public static PhoneNumberBuilder byPhoneNumber() {
        return new ReceiverRequest().new PhoneNumberBuilder();
    }

    public static BirthInformationBuilder byBirthInformation() {
        return new ReceiverRequest.BirthInformationBuilder();
    }

    public static AddressBookBuilder byAddressBook() {
        return new ReceiverRequest.AddressBookBuilder();
    }

    /**
     * gets the accountId
     *
     * @return the accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * gets the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * gets the tel
     *
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * gets the fullName
     *
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * gets the firstName
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * gets the lastName
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * gets the dateOfBirth
     *
     * @return the dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * gets the vatId
     *
     * @return the vatId
     */
    public String getVatId() {
        return vatId;
    }

    /**
     * gets the ssn
     *
     * @return the ssn
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * gets the birthInformation
     *
     * @return the birthInformation
     */
    public ReceiverBirthInformation getBirthInformation() {
        return birthInformation;
    }

    /**
     * gets the addressBook
     *
     * @return the addressBook
     */
    public ReceiverAddressBook getAddressBook() {
        return addressBook;
    }

    public static class ReceiverBirthInformation {
        @JsonProperty("date_of_birth")
        private String dateOfBirth;
        @JsonProperty("place_of_birth")
        private String placeOfBirth;
        @JsonProperty("nationality")
        private String nationality;
        @JsonProperty("given_names")
        private String givenNames;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("birth_name")
        private String birthName;
        // getters





        /**
         * gets the dateOfBirth
         *
         * @return the dateOfBirth
         */
        public String getDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * gets the placeOfBirth
         *
         * @return the placeOfBirth
         */
        public String getPlaceOfBirth() {
            return placeOfBirth;
        }

        /**
         * gets the nationality
         *
         * @return the nationality
         */
        public String getNationality() {
            return nationality;
        }

        /**
         * gets the givenNames
         *
         * @return the givenNames
         */
        public String getGivenNames() {
            return givenNames;
        }

        /**
         * gets the lastName
         *
         * @return the lastName
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * gets the birthName
         *
         * @return the birthName
         */
        public String getBirthName() {
            return birthName;
        }
    }


    public static class ReceiverAddressBook {
        @JsonProperty("id")
        private String id;
        @JsonProperty("owner")
        private String owner;
        // getters




        /**
         * gets the id
         *
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * gets the owner
         *
         * @return the owner
         */
        public String getOwner() {
            return owner;
        }
    }



    public static class BirthInformationBuilder {
        private String dateOfBirth;
        private String placeOfBirth;
        private String firstName;
        private String lastName;
        private String nameAtBirth;
        private String nationality;

        protected BirthInformationBuilder() {
        }

        public BirthInformationBuilder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public BirthInformationBuilder withPlaceOfBirth(String placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
            return this;
        }

        public BirthInformationBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public BirthInformationBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public BirthInformationBuilder withNameAtBirth(String nameAtBirth) {
            this.nameAtBirth = nameAtBirth;
            return this;
        }

        public BirthInformationBuilder withNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public ReceiverBirthInformation build() {
            ReceiverBirthInformation birthInformation = new ReceiverBirthInformation();
            birthInformation.dateOfBirth = this.dateOfBirth;
            birthInformation.placeOfBirth = this.placeOfBirth;
            birthInformation.givenNames = this.firstName;
            birthInformation.lastName = this.lastName;
            birthInformation.birthName = this.nameAtBirth;
            birthInformation.nationality = this.nationality;
            return birthInformation;
        }

        public ReceiverRequest buildRequest() {
            ReceiverRequest request = new ReceiverRequest();
            request.birthInformation = build();
            return request;
        }
    }


    public static interface ContactDataBuilder{


        public ContactDataBuilder withDateOfBirth(String dateOfBirth);
        public ContactDataBuilder withName(String name);

        public String getName();

        public String getDateOfBirth();
        public ReceiverRequest buildRequest();
    }

    public static class EmailBuilder implements ContactDataBuilder {
        private String email;
        private String name;
        private String dateOfBirth;

        protected EmailBuilder() {
        }



        public EmailBuilder withEmail(String email) {
            this.email = email;
            return this;
        }


        @Override
        public ContactDataBuilder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        @Override
        public ContactDataBuilder withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getDateOfBirth() {
            return this.dateOfBirth;
        }

        public ReceiverRequest buildRequest() {
            ReceiverRequest request = new ReceiverRequest();
            request.email = this.email;
            request.fullName = this.name;
            request.dateOfBirth = this.dateOfBirth;
            return request;
        }
    }

    public class PhoneNumberBuilder implements ContactDataBuilder{
        private String phoneNumber;
        private String name;
        private String dateOfBirth;

        protected PhoneNumberBuilder() {
        }

        public PhoneNumberBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        @Override
        public ContactDataBuilder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        @Override
        public ContactDataBuilder withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getDateOfBirth() {
            return this.dateOfBirth;
        }

        public ReceiverRequest buildRequest() {
            ReceiverRequest request = new ReceiverRequest();
            request.tel = this.phoneNumber;
            request.fullName = this.name;
            request.dateOfBirth = this.dateOfBirth;
            return request;
        }
    }

    public static class AddressBookBuilder {
        private String id;
        private String owner;

        protected AddressBookBuilder() {
        }

        public AddressBookBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public AddressBookBuilder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public ReceiverAddressBook build() {
            ReceiverAddressBook addressBook = new ReceiverAddressBook();
            addressBook.id = this.id;
            addressBook.owner = this.owner;
            return addressBook;
        }

        public ReceiverRequest buildRequest() {
            ReceiverRequest request = new ReceiverRequest();
            request.addressBook = build();
            return request;
        }
    }





}


