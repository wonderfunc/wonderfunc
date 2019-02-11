package containers;

import interfaces.Unit;
import interfaces.Relay;
import interfaces.Target;

import java.io.Serializable;

public class SourceUnit<I extends Serializable> implements Relay<I>, Unit<I> {

    private final Iterable<I> iterable;
    private Target<I> target;

    public SourceUnit(Iterable<I> iterable) {
        this.iterable = iterable;
    }

    @Override
    public void next(Target<I> target) {
        this.target = target;
    }

    @Override
    public void relay(I data) {
        target.put(data);
    }
}
