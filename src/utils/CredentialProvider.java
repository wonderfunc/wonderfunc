package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CredentialProvider {

    public static AWSCredentials awsCredentials(String rootKeyFile) {
        AWSCredentials awsCredentials = new AWSCredentials("", "");
        try {
            awsCredentials = new AWSCredentials(getAccessKeyID(rootKeyFile), getSecretAccessKey(rootKeyFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return awsCredentials;
    }

    private static String getSecretAccessKey(String rootKeyFile) throws IOException {
        return Files.readAllLines(Paths.get(rootKeyFile)).get(1).split("=")[1];
    }

    private static String getAccessKeyID(String rootKeyFile) throws IOException {
        return Files.readAllLines(Paths.get(rootKeyFile)).get(0).split("=")[1];
    }



}
