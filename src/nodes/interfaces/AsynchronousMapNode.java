package nodes.interfaces;

import message.DataMessage;
import message.Message;
import nodes.algorithmia.AsynchronousLambdaRepositoryExecutor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AsynchronousMapNode <T extends Serializable, R extends Serializable> implements Node<R>, Target<T>, Relay<R> {

    private final int cacheSize;
    private List<Message<T>> cache;
    private final Function function;
    private Target<R> target;

    public AsynchronousMapNode(Function function, int cacheSize) {
        this.cacheSize = cacheSize;
        this.function = function;
    }

    public AsynchronousMapNode(Function function) {
        this(function, 20);
    }

    @Override
    public void push(Message<T> message) {
        if (isADataMessage(message)) {
            pushToCache(message);
        } else {
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

    public void relayAll(String output) {
        deserialize(output).forEach(this::relay);
    }

    private boolean isADataMessage(Message<T> message) {
        return message instanceof DataMessage;
    }

    private void pushToCache(Message<T> message) {
        if (thereIsSpaceInCache()) cache.add(message);
        else sendAsynchronousBathJob();
    }

    private boolean thereIsSpaceInCache() {
        return cacheSize > cache.size();
    }

    private void sendAsynchronousBathJob() {
        List<Message> messages = new ArrayList<>(cache);
        cache = new ArrayList<>();
        String inputJSON = serialize(messages);
        new Thread(new AsynchronousLambdaRepositoryExecutor(inputJSON, this, function)).start();
    }

    protected abstract String serialize(List<Message> messages);

    protected abstract List<Message> deserialize(String output);

}
