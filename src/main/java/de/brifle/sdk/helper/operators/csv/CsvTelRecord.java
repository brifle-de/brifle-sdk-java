package de.brifle.sdk.helper.operators.csv;

import de.brifle.sdk.helper.IsoDateConverter;

import java.util.Date;

public class CsvTelRecord implements CsvReceiverRecordType{

    private String phoneNumber;
    private String name;
    private Date dateOfBirth;

    private String path;

    private String externalId;


    private CsvTelRecord(Builder builder) {
        phoneNumber = builder.phoneNumber;
        name = builder.name;
        dateOfBirth = builder.dateOfBirth;
        path = builder.path;
        externalId = builder.externalId;
    }



    /**
     * gets the email
     *
     * @return the email
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * gets the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the dateOfBirth
     *
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
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

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String[] getHeader() {
        return new String[]{
            "external_id",
            "phone_number",
            "name",
            "date_of_birth",
            "path"
        };
    }

    @Override
    public String[] getRecord() {
        return new String[]{
            externalId,
            phoneNumber,
            name,
            IsoDateConverter.toIsoStringShort(dateOfBirth),
            path
        };
    }

    @Override
    public CsvReceiverRecordType setValues(String[] values) {
        if(values.length != 5){
            throw new IllegalArgumentException("values length must be "+values.length);
        }
        externalId = values[0];
        phoneNumber = values[1];
        name = values[2];
        try {
            dateOfBirth = IsoDateConverter.fromIsoStringShort(values[3]);
        } catch (Exception e) {
            dateOfBirth = null;
        }
        path = values[4];
        return this;
    }


    public static final class Builder {
        private String phoneNumber;
        private String name;
        private Date dateOfBirth;

        private String path;

        private String externalId;

        public Builder() {
        }

        public Builder withPhoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withDateOfBirth(Date val) {
            dateOfBirth = val;
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

        public CsvTelRecord build() {
            return new CsvTelRecord(this);
        }
    }
}
