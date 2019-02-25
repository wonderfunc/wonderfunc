package operations;

import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;
import stream.Stream;

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

    @Override
    public void put(T data) {
        relay(function.apply(data));
    }


    @Override
    public void relay(R data) {
        next.put(data);
    }
}
