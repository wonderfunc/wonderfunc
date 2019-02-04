import java.io.Serializable;
import java.util.function.Function;

public class Container<I extends Serializable, O extends Serializable> implements Target<I>, Generator<O> {
    private Function<I, O> function;

    public Container(Function<I, O> function) {
        this.function = function;
    }

    @Override
    public void setTarget(Target target) {

    }
}
