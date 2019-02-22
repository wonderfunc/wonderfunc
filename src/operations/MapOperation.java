package operations;

import stream.Stream;

import java.io.Serializable;
import java.util.function.Function;

public class MapOperation<T extends Serializable, R extends Serializable> implements Operation{
    private Function<T, R> function;
    private Stream stream;

    public MapOperation(Function<T, R> function, Stream stream) {
        this.function = function;
        this.stream = stream;
    }

    @Override
    public void put(Serializable data) {

    }
}
