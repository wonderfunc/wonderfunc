import java.io.Serializable;
import java.util.function.Function;

public class MapContainer<I extends Serializable, O extends Serializable> implements Target<I>, Generator<O> {
    private Function<I, O> function;
    private Target target;

    public MapContainer(Function<I, O> function) {
        this.function = function;
    }

    @Override
    public void setTarget(Target target) {
        this.target = target;
    }

    public void yield(I input) {
        sendToTarget(function.apply(input));
    }

    private void sendToTarget(O output) {

    }
}
