package containers;

import interfaces.Unit;
import interfaces.Relay;
import interfaces.Target;

import java.io.Serializable;
import java.util.function.Predicate;

public class FilterUnit<T extends Serializable> implements Target<T>, Unit<T>, Relay<T> {

    private Predicate<T> predicate;
    private Target<T> target;

    public FilterUnit(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void next(Target<T> target) {
        this.target = target;
    }

    @Override
    public void put(T data) {
        if (predicate.test(data)) relay(data);
    }

    @Override
    public void relay(T data) {
        target.put(data);
    }
}
