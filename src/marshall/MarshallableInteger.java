package marshall;

public class MarshallableInteger implements Marshallable<Integer> {

    @Override
    public String marshall(Integer messageData) {
        return messageData.toString();
    }

    @Override
    public Integer unmarshall(String output) {
        return Integer.parseInt(output);
    }

}
