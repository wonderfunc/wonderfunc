package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CredentialProvider {

    public static AWSCredentials awsCredentials(String rootKeyFile) {
        try (Reader reader = Files.newBufferedReader(Paths.get(rootKeyFile));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            List<CSVRecord> records = csvParser.getRecords();
            return new AWSCredentials(records.get(0).get(0).split("=")[1],
                    records.get(1).get(0).split("=")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AWSCredentials("", "");
    }

}
