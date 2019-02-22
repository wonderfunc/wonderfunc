package operations;

import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;
import stream.Stream;

import java.io.Serializable;
import java.util.function.Predicate;

public class FilterOperation<T extends Serializable> implements Operation<T>, Relay<T>, Target<T> {

    private Predicate<T> predicate;
    private Stream<T> stream;

    public FilterOperation(Predicate<T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public void next(Target<T> target) {

    }

    @Override
    public void relay(T data) {

    }

    @Override
    public void put(T data) {

    }
}
