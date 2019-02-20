package units;

import interfaces.Relay;
import interfaces.Target;
import interfaces.Unit;

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

    public void relayAll() {
        for(I data : iterable) relay(data);
    }

    @Override
    public void relay(I data) {
        target.put(data);
    }

}
