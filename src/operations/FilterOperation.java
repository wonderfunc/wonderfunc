package operations;

import message.Message;
import message.MessageType;
import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;

import java.io.Serializable;
import java.util.function.Predicate;

public class FilterOperation<T extends Serializable> implements Operation<T>, Relay<T>, Target<T> {

    private Predicate<T> predicate;
    private Target<T> next;

    public FilterOperation(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void next(Target<T> target) {
        this.next = target;
    }

    @Override
    public void put(Message<T> message) {
        if (message.type() == MessageType.DATA)
            if (predicate.test(message.data())) relay(message);
        else
            relay(message);
    }

    @Override
    public void relay(Message<T> message) {
        next.put(message);
    }
}
