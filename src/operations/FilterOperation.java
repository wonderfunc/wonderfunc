package operations;

import stream.Stream;

import java.io.Serializable;
import java.util.function.Predicate;

public class FilterOperation<T extends Serializable> implements Operation<T> {

    private Predicate<T> predicate;
    private Stream<T> stream;

    public FilterOperation(Predicate<T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public void put(T data) {
        if (predicate.test(data)) stream.nextOperation().put(data);
    }
}
