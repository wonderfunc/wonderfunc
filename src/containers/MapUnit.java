package containers;

import interfaces.Unit;
import interfaces.Relay;
import interfaces.Target;

import java.io.Serializable;
import java.util.function.Function;

public class MapUnit<T extends Serializable, R extends Serializable> implements Target<T>, Unit<R>, Relay<R> {
    private Function<T, R> function;
    private Target<R> target;

    public MapUnit(Function<T, R> function) {
        this.function = function;
    }

    @Override
    public void next(Target<R> target) {
        this.target = target;
    }

    @Override
    public void put(T data) {
        relay(function.apply(data));
    }

    @Override
    public void relay(R input) {
        target.put(input);
    }
}
