package nodes.local;

import message.DataMessage;
import message.Message;
import nodes.interfaces.FilterNode;
import nodes.interfaces.Target;

import java.io.Serializable;
import java.util.function.Predicate;

public class LocalFilterNode<T extends Serializable> implements FilterNode<T> {

    private final Predicate<T> predicate;
    private Target<T> target;

    public LocalFilterNode(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(DataMessage<T> dataMessage) {
        return predicate.test(dataMessage.data());
    }

    @Override
    public void next(Target<T> target) {
        this.target = target;
    }

    @Override
    public void put(Message<T> message) {
        if (isADataMessage(message)) {
            if (test((DataMessage<T>) message)) relay(message);
        } else {
            relay(message);
        }
    }

    private boolean isADataMessage(Message<T> message) {
        return message instanceof DataMessage;
    }

    @Override
    public void relay(Message<T> message) {
        target.put(message);
    }
}
