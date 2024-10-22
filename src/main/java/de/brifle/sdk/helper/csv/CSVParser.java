package de.brifle.sdk.helper.csv;

import de.brifle.sdk.helper.Crypto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class CSVParser {

        private String[] header;
        private List<String[]> rows = new LinkedList<>();

        private List<String> hmacs = new LinkedList<>();

        private final byte[] macKey;

        private final CSVFormat csvFormat;

        public final static String HMAC_HEADER = "__HMAC__";

        private boolean hasHMac;

        public CSVParser(byte[] macKey) {
            this.csvFormat = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(false)
                    .build();
            this.macKey = macKey;
        }

        public CSVParser(String macKey) {
            this(Crypto.hexToBytes(macKey));
        }

    /**
     * read a csv file and parse it. If validateHMAC is set to true, the HMAC header must be present in the file and the HMACs will be validated.
     * @param path
     * @param validateHMAC
     * @return
     * @throws IOException
     * @throws HMacHeaderMissingException if the HMAC header is missing and validateHMAC is set to true
     * @throws HMacValidationFailedException if the HMAC validation fails, a rows was tampered with
     */
        public CSVParser read(String path, boolean validateHMAC) throws IOException, HMacHeaderMissingException, HMacValidationFailedException {
            Reader in = new FileReader(path);
            Iterable<CSVRecord> records = csvFormat.parse(in);

            CSVRecord h = records.iterator().next();
            // get the header
            header = new String[h.size()];
            for (int i = 0; i < h.size(); i++) {
                header[i] = h.get(i);
            }
            this.hasHMac = header[0].equals(HMAC_HEADER);

            // check if the HMAC header is missing
            if (validateHMAC && !this.hasHMac) {
                throw new HMacHeaderMissingException();
            }

            // remove the HMAC header
            if(this.hasHMac){
                // remove the HMAC header
                String[] newHeader = new String[header.length - 1];
                System.arraycopy(header, 1, newHeader, 0, header.length - 1);
                header = newHeader;
            }

            // add rows
            loadRows(records, validateHMAC);


            return this;
        }

    private void loadRows(Iterable<CSVRecord> records , boolean validateHMAC) throws HMacValidationFailedException {
        while (records.iterator().hasNext()) {
            CSVRecord record = records.iterator().next();
            String[] row = new String[record.size()];
            for (int i = 0; i < record.size(); i++) {
                row[i] = record.get(i);
            }
            if(this.hasHMac){
                this.hmacs.add(row[0]);
                // remove the HMAC header
                String[] newRow = new String[row.length - 1];
                System.arraycopy(row, 1, newRow, 0, row.length - 1);
                this.rows.add(newRow);
                if(validateHMAC){
                    try {
                        if(!validateHMAC(newRow, row[0])){
                            throw new HMacValidationFailedException();
                        }
                    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                        throw new HMacValidationFailedException();
                    }
                }
            }else{
                this.rows.add(row);
            }
        }
    }

    private boolean validateHMAC(String[] row, String expectedHmac) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeyException {
        String hmac = Crypto.hmac(String.join("", row), Crypto.bytesToHex(macKey));
        return hmac.equals(expectedHmac);
    }


    public String[] getHeader() {
        return header;
    }

    public String[][] getRows() {
        return rows.toArray(new String[0][0]);
    }
}
