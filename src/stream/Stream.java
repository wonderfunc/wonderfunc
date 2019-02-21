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
    private List<Operation> operations = new ArrayList<>();
    private Target target;

    public Stream(Source source) {
        this.source = source;
    }

    public static <T extends Serializable> Stream<T> source(List<T> list) {
        return new Stream<>(new Source(list));
    }

    public Stream<T> filter(Predicate<T> predicate) {
        operations.add(new FilterOperation<>(predicate));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        operations.add(new MapOperation<>(function));
        return (Stream<R>) this;
    }

    public Thread collectTo(List<T> list) {
        this.target = new Target<>(list);
        return new StreamThread().deploy();
    }

    private class StreamThread extends Thread {


        public Thread deploy() {

            this.start();
            return this;
        }
    }
}
