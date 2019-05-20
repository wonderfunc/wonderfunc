package marshall;

import java.io.Serializable;

public class MarshallableComment implements Marshallable {
    @Override
    public String marshall(Serializable messageData) {
        return "{\"document\": \"" + messageData + "\"}";
    }

    @Override
    public Serializable unmarshall(String output) {
        return Double.parseDouble(output.substring(output.indexOf(":") + 1, output.indexOf(",")));
    }
}
