package operations;

import stream.Stream;

import java.io.Serializable;
import java.util.function.Predicate;

public class FilterOperation<T extends Serializable> implements Operation {

    private Predicate<T> predicate;
    private Stream stream;

    public FilterOperation(Predicate<T> predicate, Stream stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public void put(Serializable data) {

    }
}
