package operations;

import stream.Stream;

import java.io.Serializable;
import java.util.function.Function;

public class MapOperation<T extends Serializable, R extends Serializable> implements Operation<T> {
    private Function<T, R> function;
    private Stream<T> stream;

    public MapOperation(Function<T, R> function, Stream<T> stream) {
        this.function = function;
        this.stream = stream;
    }

    @Override
    public void put(T data) {
        stream.nextOperation().put(function.apply(data));
    }
}
