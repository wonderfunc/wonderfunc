package stream;

import message.Message;
import message.MessageType;
import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;

import java.io.Serializable;
import java.util.List;

public class Source<T extends Serializable> implements Operation<T>, Relay<T> {

    private final List<T> list;
    private Target<T> next;

    public Source(List<T> list) {
        this.list = list;
    }

    public void relayAll() {
        for (T data : list) relay(new Message<>(data, MessageType.DATA));
        relay(new Message<>(MessageType.ENDOFSTREAM));
    }

    @Override
    public void next(Target<T> target) {
        this.next = target;
    }

    @Override
    public void relay(Message<T> message) {
        next.put(message);
    }
}