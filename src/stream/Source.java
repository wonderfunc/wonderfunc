package stream;

import java.io.Serializable;
import java.util.List;

public class Source<T extends Serializable> {

    private final List<T> list;
    private final Stream<T> stream;

    public Source(List<T> list, Stream<T> stream) {
        this.list = list;
        this.stream = stream;
    }

    public void relayAll() {
        for (T data : list) stream.nextOperation().put(data);
    }

}