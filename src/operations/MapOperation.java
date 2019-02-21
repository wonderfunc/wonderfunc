package operations;

import java.io.Serializable;
import java.util.function.Function;

public class MapOperation<T extends Serializable, R extends Serializable> implements Operation{
    private Function<T, R> function;

    public MapOperation(Function<T, R> function) {
        this.function = function;
    }
}
