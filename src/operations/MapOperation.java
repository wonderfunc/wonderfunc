package operations;

import message.Message;
import message.MessageType;
import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;

import java.io.Serializable;
import java.util.function.Function;

public class MapOperation<T extends Serializable, R extends Serializable> implements Operation<R>, Relay<R>, Target<T> {
    private Function<T, R> function;
    private Target<R> next;

    public MapOperation(Function<T, R> function) {
        this.function = function;
    }

    @Override
    public void next(Target<R> target) {
        this.next = target;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void put(Message<T> message) {
        if (message.type() == MessageType.DATA)
            relay(new Message<>(function.apply(message.data()), MessageType.DATA));
        else
            relay((Message<R>)message);
    }

    @Override
    public void relay(Message<R> message) {
        next.put(message);
    }
}
