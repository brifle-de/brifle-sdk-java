package de.brifle.sdk.helper.operators.csv;

public interface CsvReceiverRecordType {

    String[] getHeader();

    String[] getRecord();

    /**
     * set values as string array and return the record
     * @param values
     * @throws IllegalArgumentException if the values array is not the same length as the header
     * @return
     */
    CsvReceiverRecordType setValues(String[] values);
}
