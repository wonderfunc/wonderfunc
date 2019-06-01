package marshall;

public class MarshallingInteger implements Marshalling<Integer, Integer> {

    @Override
    public String marshall(Integer messageData) {
        return messageData.toString();
    }

    @Override
    public Integer unmarshall(String output) {
        return Integer.parseInt(output);
    }

}
