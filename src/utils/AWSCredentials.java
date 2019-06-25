package utils;

public class AWSCredentials {

    private final String ACCESS_KEY_ID;
    private final String SECRET_ACCESS_KEY;

    public AWSCredentials(String ACCESS_KEY_ID, String SECRET_ACCESS_KEY) {
        this.ACCESS_KEY_ID = ACCESS_KEY_ID;
        this.SECRET_ACCESS_KEY = SECRET_ACCESS_KEY;
    }

    public String getACCESS_KEY_ID() {
        return ACCESS_KEY_ID;
    }

    public String getSECRET_ACCESS_KEY() {
        return SECRET_ACCESS_KEY;
    }
}
