package de.brifle.sdk.helper;

import de.brifle.sdk.helper.csv.CSVBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CSVBuilderTest {

    @Test
    public void buildCSV() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String[] header = {
                "Name",
                "Age",
                "City"
        };
        CSVBuilder csvBuilder = new CSVBuilder(header);
        csvBuilder.addRow(new String[]{"John", "25", "New York"});
        csvBuilder.addRow(new String[]{"Jane", "30", "Los Angeles"});
        csvBuilder.addRow(new String[]{"Alice", "22", "Chicago"});
        csvBuilder.addRow(new String[]{"Bob", "35", "San Francisco"});

        System.out.println(csvBuilder.build());
    }
}
