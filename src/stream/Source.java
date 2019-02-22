package stream;

import operations.Operation;

import java.util.List;

public class Source<T> {

    private final List<T> list;
    private final Stream stream;

    public Source(List<T> list, Stream stream) {
        this.list = list;
        this.stream = stream;
    }

    public void relayAll() {
        final Operation operation = stream.nextOperation();
        for(T data : list) operation.put(data);
    }
}
