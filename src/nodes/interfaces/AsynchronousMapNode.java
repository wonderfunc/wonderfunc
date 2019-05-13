package nodes.interfaces;

import message.DataMessage;
import message.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import repositories.AsynchronousFunction;
import repositories.LambdaRepositoryExecutor;
import repositories.Listener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AsynchronousMapNode <T extends Serializable, R extends Serializable> implements Node<R>, Target<T>, Relay<R>, Listener {

    private final int cacheSize;
    private List<Message<T>> receivedDataMessages;
    private final AsynchronousFunction function;
    private Target<R> target;
    private Thread executingThread;
    private int cachedMessagesAmount;

    public AsynchronousMapNode(AsynchronousFunction function, int cacheSize) {
        this.cacheSize = cacheSize;
        this.function = function;
        this.receivedDataMessages = new ArrayList<>();
        this.cachedMessagesAmount = 0;
    }

    public AsynchronousMapNode(AsynchronousFunction function) {
        this(function, 5);
    }

    @Override
    public void push(Message<T> message) {
        if (isADataMessage(message)) {
            pushToCache(message);
        } else {
            if (thereAreMessagesLeftInCache()) sendAsynchronousBathJob();
            try {
                executingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            relay((Message<R>) message);
        }
    }

    @Override
    public void relay(Message<R> message) {
        target.push(message);
    }

    @Override
    public void next(Target<R> target) {
        this.target = target;
    }

    @Override
    public synchronized void onFinish(String output) {
        unmarshall(output).forEach(this::relay);
    }

    private boolean isADataMessage(Message<T> message) {
        return message instanceof DataMessage;
    }

    private void pushToCache(Message<T> message) {
        cachedMessagesAmount++;
        receivedDataMessages.add(message);
        if (!thereIsSpaceInCache()) sendAsynchronousBathJob();
    }

    private boolean thereIsSpaceInCache() {
        return cacheSize > cachedMessagesAmount;
    }

    private void sendAsynchronousBathJob() {
        if (!thereIsExecutingThreadRunning()) {
            List<Message<T>> messages = sliceReceivedMessages();
            cleanCache();
            String inputJSON = marshall(messages);
            executingThread = new Thread(new LambdaRepositoryExecutor(inputJSON, function).addOnFinishListener(this));
            executingThread.start();
            try {
                executingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean thereIsExecutingThreadRunning() {
        return executingThread != null && executingThread.isAlive();
    }

    private List<Message<T>> sliceReceivedMessages() {
        List<Message<T>> messages = receivedDataMessages.subList(0, cachedMessagesAmount);
        receivedDataMessages = receivedDataMessages.subList(cachedMessagesAmount, receivedDataMessages.size());
        return messages;
    }

    private void cleanCache() {
        cachedMessagesAmount = 0;
    }

    private String marshall(List list) {

        JSONArray messagesData = new JSONArray();
        for (Object each : list) {
            final String marshall = function.marshall(((DataMessage) each).data());
            messagesData.put(marshall);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("data", messagesData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();

    }

    private boolean thereAreMessagesLeftInCache() {
        return cachedMessagesAmount > 0;
    }

    private List<Message> unmarshall(String output) {
        List<Message> deserializeMessages = new ArrayList<>();
        JSONObject json;
        try {
            json = new JSONObject(fixOutputString(output));
            JSONArray data = json.getJSONArray("data");
            for (int i = 0; i < data.length(); i++)
                deserializeMessages.add(new DataMessage(function.unmarshall((String)data.get(i))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deserializeMessages;
    }

    private String fixOutputString(String output) {
        return output.substring(1, output.length() - 1).replace("\\\"", "\"");
    }


}
