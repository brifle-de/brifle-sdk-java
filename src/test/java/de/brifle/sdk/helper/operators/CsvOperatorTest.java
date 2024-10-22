package de.brifle.sdk.helper.operators;

import de.brifle.sdk.helper.csv.HMacHeaderMissingException;
import de.brifle.sdk.helper.csv.HMacValidationFailedException;
import de.brifle.sdk.helper.operators.csv.CsvBirthInformationRecord;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CsvOperatorTest {

    @Test
    public void createFile() throws Exception {
        CsvOperator<CsvBirthInformationRecord> csvOperator = new CsvOperator<>(CsvBirthInformationRecord.class);

        // test ressource folder
        URL url = this.getClass().getClassLoader().getResource("operators");
        String path = url.getPath() + "/test.csv";


        List<CsvBirthInformationRecord> records = List.of(
                CsvBirthInformationRecord.builder()
                        .withExternalId("1")
                        .withFirstName("Max")
                        .withLastName("Mustermann")
                        .withBirthDate("1998-04-02")
                        .withBirthPlace("Musterstadt")
                        .withPath("file1")
                        .build(),
                CsvBirthInformationRecord.builder()
                        .withExternalId("2")
                        .withFirstName("Maxine")
                        .withLastName("Musterfrau")
                        .withBirthDate("2000-01-01")
                        .withBirthPlace("Musterstadt")
                        .withPath("file2")
                        .build()
        );

        File res = csvOperator.writeCsvFile(path,records);
        String macKey = csvOperator.getMacKey();
        assert macKey != null;

        assert res.exists();

    }

    @Test
    public void readCorrect(){
        String macKey = "E1485697814619123D96743F04CE44BAA16FDB37996E5F502A30448739C9F3DF";
        CsvOperator<CsvBirthInformationRecord> csvOperator = new CsvOperator<>(CsvBirthInformationRecord.class,macKey);
        // read the file
        String path = this.getClass().getClassLoader().getResource("operators/csv/expected.csv").getPath();
        try {
            List< CsvBirthInformationRecord> res = csvOperator.readCsvFile(path);
            assert res.size() == 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readTamperedCorrect(){
        String macKey = "E1485697814619123D96743F04CE44BAA16FDB37996E5F502A30448739C9F3DF";
        CsvOperator<CsvBirthInformationRecord> csvOperator = new CsvOperator<>(CsvBirthInformationRecord.class,macKey);
        // read the file
        String path = this.getClass().getClassLoader().getResource("operators/csv/expected-tampered.csv").getPath();
        try {
            List< CsvBirthInformationRecord> res = csvOperator.readCsvFile(path);
            assert false;
        } catch (HMacValidationFailedException e) {
            // expects exception
            assert true;
        } catch (IOException | HMacHeaderMissingException e) {
            assert false;
        }
    }

    @Test
    public void readWrongKeyCorrect(){
        String macKey = "E2485697814619123D96743F04CE44BAA16FDB37996E5F502A30448739C9F3DF";
        CsvOperator<CsvBirthInformationRecord> csvOperator = new CsvOperator<>(CsvBirthInformationRecord.class,macKey);
        // read the file
        String path = this.getClass().getClassLoader().getResource("operators/csv/expected.csv").getPath();
        try {
            List< CsvBirthInformationRecord> res = csvOperator.readCsvFile(path);
            assert false;
        } catch (HMacValidationFailedException e) {
            // expects exception
            assert true;
        } catch (IOException | HMacHeaderMissingException e) {
            assert false;
        }
    }
}
