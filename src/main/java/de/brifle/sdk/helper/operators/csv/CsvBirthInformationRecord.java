package de.brifle.sdk.helper.operators.csv;

import de.brifle.sdk.helper.IsoDateConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CsvBirthInformationRecord implements CsvReceiverRecordType{


    private String firstName;
    private String lastName;
    private Date birthDate;
    private String birthPlace;

    // i.e. a employee number
    private String externalId;

    private String path;

    private CsvBirthInformationRecord(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        birthDate = builder.birthDate;
        birthPlace = builder.birthPlace;
        externalId = builder.externalId;
        path = builder.path;
    }

    public static Builder builder() {
        return new Builder();
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
     * gets the birthDate
     *
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * gets the birthPlace
     *
     * @return the birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * gets the path
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * gets the externalId
     *
     * @return the externalId
     */
    public String getExternalId() {
        return externalId;
    }

    @Override
    public String[] getHeader() {
        return new String[]{
                "external_id",
                "first_name",
                "last_name",
                "birth_date",
                "birth_place",
                "path",
        };
    }

    @Override
    public String[] getRecord() {
        return new String[]{
                externalId,
                firstName,
                lastName,
                IsoDateConverter.toIsoString(birthDate),
                birthPlace,
                path
        };
    }

    @Override
    public CsvReceiverRecordType setValues(String[] values) {
        if (values.length != 6) {
            throw new IllegalArgumentException("values length must be 5");
        }
        externalId = values[0];
        firstName = values[1];
        lastName = values[2];
        try {
            birthDate = IsoDateConverter.fromIsoString(values[3]);
        } catch (Exception e) {
            birthDate = null;
        }
        birthPlace = values[4];
        path = values[5];
        return this;
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private Date birthDate;
        private String birthPlace;

        private String externalId;

        private String path;

        public Builder() {
        }

        public Builder withFirstName(String val) {
            firstName = val;
            return this;
        }

        public Builder withLastName(String val) {
            lastName = val;
            return this;
        }

        public Builder withBirthDate(Date val) {
            birthDate = val;
            return this;
        }

        public Builder withBirthDate(String val) throws Exception {
            birthDate = IsoDateConverter.fromIsoStringShort(val);
            return this;
        }

        public Builder withBirthPlace(String val) {
            birthPlace = val;
            return this;
        }

        public Builder withPath(String val) {
            path = val;
            return this;
        }

        public Builder withExternalId(String val) {
            externalId = val;
            return this;
        }

        public CsvBirthInformationRecord build() {
            return new CsvBirthInformationRecord(this);
        }
    }
}
