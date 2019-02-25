package stream;

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
    private OutputTarget target;

    public Stream(List<T> list) {
        this.source = new Source<>(list, this);
        operations.add(source);
    }

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
        this.target = new OutputTarget<>(list);
        return new StreamThread().deploy();
    }

    private class StreamThread extends Thread {


        public Thread deploy() {
            this.start();
            return this;
        }

        @Override
        public synchronized void start() {
            super.start();
            source.relayAll();
        }
    }
}
