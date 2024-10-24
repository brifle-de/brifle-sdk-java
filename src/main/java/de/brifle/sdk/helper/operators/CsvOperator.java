package de.brifle.sdk.helper.operators;

import de.brifle.sdk.helper.Crypto;
import de.brifle.sdk.helper.csv.CSVBuilder;
import de.brifle.sdk.helper.csv.CSVParser;
import de.brifle.sdk.helper.csv.HMacHeaderMissingException;
import de.brifle.sdk.helper.csv.HMacValidationFailedException;
import de.brifle.sdk.helper.operators.csv.CsvReceiverRecordType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvOperator<Type extends CsvReceiverRecordType> {

    Class<Type> recordType;

    String macKey;

    public CsvOperator(Class<Type> recordType) {
        this.recordType = recordType;
        try {
            this.macKey = Crypto.bytesToHex(Crypto.randomKey());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public CsvOperator(Class<Type> recordType, String macKey) {
        this.recordType = recordType;
        this.macKey = macKey;
    }

    /**
     * gets the macKey
     *
     * @return the macKey
     */
    public String getMacKey() {
        return macKey;
    }

    /**
     * write records to csv file
     * @param path path to the file
     * @param records
     * @return
     */
    public File writeCsvFile(String path, List<Type> records) {
        // create new record
        Type header = null;
        try {
            header = recordType.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            header = records.get(0);
        }
        // write record to csv file
        String[] headers = header.getHeader();

        try {
            CSVBuilder csvBuilder = new CSVBuilder(macKey, headers);
            for (Type record : records) {
                csvBuilder.addRow(record.getRecord());
            }
            return csvBuilder.writeToFile(path);
        } catch (NoSuchAlgorithmException | IOException | InvalidKeyException e) {
            return null;
        }
    }

    /**
     * write records to csv string
     * @param records records to write
     * @return csv string
     */
    public String writeCsvString(List<Type> records) {
        // create new record
        Type header = null;
        try {
            header = recordType.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            header = records.get(0);
        }
        // write record to csv file
        String[] headers = header.getHeader();

        try {
            CSVBuilder csvBuilder = new CSVBuilder(macKey, headers);
            for (Type record : records) {
                csvBuilder.addRow(record.getRecord());
            }
            return csvBuilder.build();
        } catch (NoSuchAlgorithmException | IOException | InvalidKeyException e) {
            return null;
        }
    }

    /**
     * read records from csv file. If a macKey is provided, the HMAC will be validated and the records will be checked for tampering.
     * @param path path to the file
     * @return list of records
     * @throws HMacValidationFailedException
     * @throws HMacHeaderMissingException
     * @throws IOException
     */
    public List<Type> readCsvFile(String path) throws HMacValidationFailedException, HMacHeaderMissingException, IOException {
        // read record from csv file
        File file = new File(path);
        if(!file.exists()){
            return null;
        }

        CSVParser parser = new CSVParser(macKey);
        boolean validateHMAC = macKey != null && !macKey.isEmpty();
        parser.read(path, validateHMAC);
        String[][] records = parser.getRows();
        return Stream
            .of(records)
            .map(record -> {
                try {
                    Type newRecord = recordType.getConstructor().newInstance();
                    newRecord.setValues(record);
                    return newRecord;
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignored) {
                    return null;
                }
            })
            .filter(record -> record != null)
            .collect(Collectors.toList());
    }

    /**
     * read records from csv string. If a macKey is provided, the HMAC will be validated and the records will be checked for tampering.
     * @param csvString
     * @return
     * @throws HMacValidationFailedException
     * @throws HMacHeaderMissingException
     * @throws IOException
     */
    public List<Type> readCsvString(String csvString) throws HMacValidationFailedException, HMacHeaderMissingException, IOException {
        // read record from csv file
        CSVParser parser = new CSVParser(macKey);
        boolean validateHMAC = macKey != null && !macKey.isEmpty();
        parser.readString(csvString, validateHMAC);
        String[][] records = parser.getRows();
        return Stream
            .of(records)
            .map(record -> {
                try {
                    Type newRecord = recordType.getConstructor().newInstance();
                    newRecord.setValues(record);
                    return newRecord;
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignored) {
                    return null;
                }
            })
            .filter(record -> record != null)
            .collect(Collectors.toList());
    }



}
