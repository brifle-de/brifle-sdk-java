package de.brifle.sdk.helper.csv;

import de.brifle.sdk.helper.Crypto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CSVBuilder {

    private final byte[] macKey;
    private final String[] header;
    private final List<List<String>> rows = new LinkedList<>();
    private final CSVFormat csvFormat;

    private final StringWriter sw = new StringWriter();

    private final CSVPrinter printer;

    public final static String HMAC_HEADER = "__HMAC__";

    public CSVBuilder(byte[] macKey, String[] header) throws IOException {
        this.macKey = macKey;

        String[] headerWithHMac = new String[header.length + 1];
        headerWithHMac[0] = HMAC_HEADER;
        // copy the header to the new array
        System.arraycopy(header, 0, headerWithHMac, 1, header.length);

        this.header = headerWithHMac;
        this.csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(this.header)
                .setSkipHeaderRecord(false)
                .build();
        this.printer = new CSVPrinter(sw, csvFormat);
    }

    public CSVBuilder(String macKey, String[] header) throws IOException {
        this(Crypto.hexToBytes(macKey), header);
    }


    public CSVBuilder(String[] header) throws NoSuchAlgorithmException, IOException {
        this(Crypto.randomKey(), header);
    }

    /**
     * returns the used hmac key as hex string
     * @return the mac key as hex string
     */
    public String getHMacKey(){
        return Crypto.bytesToHex(macKey);
    }

    /**
     * add a row to the csv buffer
     * @param row
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public CSVBuilder addRow(String[] row) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        LinkedList<String> newRow = new LinkedList<>();
        for (String cell : row) {
            newRow.add(cell);
        }
        String hashValue = Stream.of(row).reduce("", (a, b) -> a + b);
        newRow.addFirst(Crypto.hmac(hashValue, Crypto.bytesToHex(macKey)));
        rows.add(newRow);
        printer.printRecord(newRow);
        return this;
    }

    /**
     * build the csv buffer
     * @return the csv buffer as string
     * @throws IOException if the buffer could not be built
     */
    public String build() throws IOException {
        return sw.toString();
    }

    /**
     * write the csv buffer to a file
     * @param path the path to the file
     * @return the file
     * @throws IOException if the file could not be written
     */
    public File writeToFile(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(build());
        writer.flush();
        writer.close();
        return file;
    }


}
