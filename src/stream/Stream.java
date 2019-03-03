package stream;

import connection.StreamThread;
import operations.FilterOperation;
import operations.MapOperation;
import operations.interfaces.Operation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<T extends Serializable> {
    private Source source;
    private List<Operation> operations = new ArrayList<>();
    private OutputTarget<T> target;

    public Stream(List<T> list) {
        this.source = new Source<>(list);
        operations.add(source);
    }

    @SuppressWarnings("unchecked")
    public Stream<T> filter(Predicate<T> predicate) {
        final FilterOperation<T> next = new FilterOperation<>(predicate);
        operations.get(operations.size() - 1).next(next);
        operations.add(next);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        final MapOperation<T, R> next = new MapOperation<>(function);
        operations.get(operations.size() - 1).next(next);
        operations.add(next);
        return (Stream<R>) this;
    }

    public Thread collectTo(List<T> list) {
        target = new OutputTarget<>(list);
        Thread streamThread = new Thread(new StreamThread<>(target));
        streamThread.start();
        source.relayAll();
        return streamThread;
    }

}
