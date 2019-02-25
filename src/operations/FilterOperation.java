package operations;

import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;
import stream.Stream;

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
    public void relay(T data) {

    }

    @Override
    public void put(T data) {

    }
}
