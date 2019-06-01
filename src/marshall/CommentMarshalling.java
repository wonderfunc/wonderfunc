package marshall;

public class CommentMarshalling implements Marshalling<String, Double> {

    @Override
    public String marshall(String messageData) {
        return "{\"document\": \"" + messageData + "\"}";
    }

    @Override
    public Double unmarshall(String output) {
        return Double.parseDouble(output.substring(output.indexOf(":") + 1, output.indexOf(",")));
    }
}
