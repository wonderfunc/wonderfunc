package stream;

import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;

import java.io.Serializable;
import java.util.List;

public class Source<T extends Serializable> implements Operation<T>, Relay<T> {

    private final List<T> list;
    private final Stream<T> stream;
    private Target<T> next;

    public Source(List<T> list, Stream<T> stream) {
        this.list = list;
        this.stream = stream;
    }

    public void relayAll() {
        for (T data : list) stream.nextOperation().put(data);

    @Override
    public void next(Target<T> target) {
        this.next = target;
    }

}