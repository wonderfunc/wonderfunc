package containers;

import interfaces.Generator;
import interfaces.Target;

import java.io.Serializable;

public class SourceContainer <I extends Serializable> implements Generator<I> {

    private final Iterable<I> iterable;
    private Target target;

    public SourceContainer(Iterable<I> iterable) {
        this.iterable = iterable;
    }

    @Override
    public void setTarget(Target<? extends Serializable> target) {
        this.target = target;
    }

    public void yield(I input) {
        sendToTarget(input);
    }


    private void sendToTarget(I input) {

    }
}
