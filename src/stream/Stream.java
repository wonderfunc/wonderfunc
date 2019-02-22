package stream;

import operations.FilterOperation;
import operations.MapOperation;
import operations.Operation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<T extends Serializable> {
    private Source source;
    private List<Operation<T>> operations = new ArrayList<>();
    private Target target;
    private int operationIndex;

    public Stream(List<T> list) {
        this.source = new Source<>(list, this);
        this.operationIndex = 0;
    }

    public Stream<T> filter(Predicate<T> predicate) {
        operations.add(new FilterOperation<>(predicate, this));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        operations.add(new MapOperation<>(function, this));
        return (Stream<R>) this;
    }

    public Thread collectTo(List<T> list) {
        this.target = new Target<>(list);
        return new StreamThread().deploy();
    }

    public Operation<T> nextOperation() {
        return operations.get(operationIndex++);
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
