package expressionExecutor;

import expressionExecutor.interfaces.ExpressionExecutor;
import functionRepository.interfaces.Dispatcher;
import marshall.Marshalling;
import message.DataMessage;
import message.Message;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RemoteExpressionExecutor implements ExpressionExecutor<String, Double>, Runnable {

    private Function<String, String> function;
    private final Marshalling<String, Double> marshalling;
    private List<Dispatcher<Double>> dispatchers;
    private List<String> receivedMessages;
    private int cachedMessagesAmount;
    private int cacheSize;

    public RemoteExpressionExecutor(Marshalling<String, Double> marshalling, int cacheSize) {
        this(marshalling);
        this.cacheSize = cacheSize;
    }

    public RemoteExpressionExecutor(Marshalling<String, Double> marshalling) {
        this.marshalling = marshalling;
        this.dispatchers = new ArrayList<>();
        receivedMessages = new ArrayList<>();
        cachedMessagesAmount = 0;
        cacheSize = 5;
    }

    @Override
    public ExpressionExecutor<String, Double> addDispatcher(Dispatcher<Double> dispatcher) {
        dispatchers.add(dispatcher);
        return this;
    }

    @Override
    public void add(Message<String> message) {
        if (message instanceof DataMessage) pushToCache((DataMessage) message);
        else if (thereAreMessagesLeftInCache()) run();
    }

    @Override
    public ExpressionExecutor function(Function function) {
        this.function = function;
        return this;
    }

    private boolean thereAreMessagesLeftInCache() {
        return cachedMessagesAmount > 0;
    }

    private void pushToCache(DataMessage<String> message) {
        cachedMessagesAmount++;
        receivedMessages.add(marshalling.marshall(message.data()));
        if (!thereIsSpaceInCache()) run();
    }

    @Override
    public void run() {

        String json = buildJSON();

        cleanCache();

        final String output = function.apply(json);

        List<DataMessage> unmarshalledMessages = unmarshall(output);

        for (Dispatcher<Double> dispatcher : dispatchers) {
            for (DataMessage<Double> message: unmarshalledMessages) {
                dispatcher.dispatch(message);
            }
        }
    }

    private String buildJSON() {
        StringBuilder jsonBuilder = new StringBuilder("[");
        List<String> marshalledMessages = sliceReceivedMessages();
        for (String each: marshalledMessages) jsonBuilder.append(each).append(",");

        return jsonBuilder.substring(0, jsonBuilder.length() - 1) + "]";
    }

    private boolean thereIsSpaceInCache() {
        return cacheSize > cachedMessagesAmount;
    }

    private List<String> sliceReceivedMessages() {
        List<String> messages = receivedMessages.subList(0, cachedMessagesAmount);
        receivedMessages = receivedMessages.subList(cachedMessagesAmount, receivedMessages.size());
        return messages;
    }

    private void cleanCache() {
        cachedMessagesAmount = 0;
    }

    private List<DataMessage> unmarshall(String output) {
        List<DataMessage> deserializeMessages = new ArrayList<>();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(output);
            for (int i = 0; i < jsonArray.length(); i++) {
                deserializeMessages.add(new DataMessage(marshalling.unmarshall(jsonArray.get(i).toString())));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deserializeMessages;
    }
}
