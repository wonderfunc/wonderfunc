package nodes.local;

import message.DataMessage;
import message.Message;
import nodes.interfaces.MapNode;
import nodes.interfaces.Target;

import java.io.Serializable;
import java.util.function.Function;

public class LocalMapNode<T extends Serializable, R extends Serializable> implements MapNode<T, R> {

    private Function<T, R> function;
    private Target<R> target;

    public LocalMapNode(Function<T, R> function) {
        this.function = function;
    }

    @Override
    public DataMessage<R> apply(DataMessage<T> dataMessage) {
        return new DataMessage<>(function.apply(dataMessage.data()));
    }

    @Override
    public void next(Target<R> target) {
        this.target = target;
    }

    @Override
    public void put(Message<T> message) {
        if (isADataMessage(message)) {
            relay(apply((DataMessage)message));
        } else {
            relay((Message<R>) message);
        }
    }

    @Override
    public void relay(Message<R> message) {
        target.put(message);
    }

    private boolean isADataMessage(Message<T> message) {
        return message instanceof DataMessage;
    }

}
