package containers;

import interfaces.Generator;
import interfaces.Target;

import java.io.Serializable;
import java.util.function.Predicate;

public class FilterContainer <I extends Serializable> implements Target<I>, Generator<I> {

    private Predicate<I> predicate;
    private Target target;

    public FilterContainer(Predicate<I> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void setTarget(Target<? extends Serializable> target) {
        this.target = target;
    }

    public void yield(I input) {
        if (predicate.test(input)) sendToTarget(input);
    }

    private void sendToTarget(I input) {

    }


}
