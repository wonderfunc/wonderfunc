package node.local;

import functionRepository.algorithmia.AlgorithmiaAsynchronousFunction;
import functionRepository.LambdaRepositoryExecutor;
import functionRepository.interfaces.Listener;
import message.DataMessage;
import message.Message;
import node.interfaces.AsynchronousMapNode;
import node.interfaces.Target;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalAsynchronousMapNode<T extends Serializable, R extends Serializable> implements AsynchronousMapNode<T, R>, Listener {

    private final int cacheSize;
    private List<Message<T>> receivedDataMessages;
    private final AlgorithmiaAsynchronousFunction function;
    private Target<R> target;
    private Thread executingThread;
    private int cachedMessagesAmount;

    public LocalAsynchronousMapNode(AlgorithmiaAsynchronousFunction function, int cacheSize) {
        this.cacheSize = cacheSize;
        this.function = function;
        this.receivedDataMessages = new ArrayList<>();
        this.cachedMessagesAmount = 0;
    }

    public LocalAsynchronousMapNode(AlgorithmiaAsynchronousFunction function) {
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

        StringBuilder marshalledMessages = new StringBuilder("[");

        for (Object each : list)
            marshalledMessages.append(function.marshallable().marshall(((DataMessage) each).data()))
                              .append(",");

        return marshalledMessages.substring(0, marshalledMessages.length() - 1) + "]";

    }

    private boolean thereAreMessagesLeftInCache() {
        return cachedMessagesAmount > 0;
    }

    private List<Message> unmarshall(String output) {
        List<Message> deserializeMessages = new ArrayList<>();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(output);
            for (int i = 0; i < jsonArray.length(); i++)
                deserializeMessages.add(new DataMessage(function.marshallable().unmarshall(jsonArray.getString(i))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deserializeMessages;
    }

}