package stream;

import containers.LambdaContainer;
import containers.LocalLambdaContainer;
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
    private LambdaContainer lambdaContainer;

    public Stream(List<T> list) {
        this.source = new Source<>(list);
        operations.add(source);
        this.lambdaContainer = new LocalLambdaContainer();
    }

    @SuppressWarnings("unchecked")
    public Stream<T> filter(Predicate<T> predicate) {
        FilterOperation<T> nextOperation = new FilterOperation<>(predicate);
        operations.get(operations.size() - 1).next(nextOperation);
        operations.add(nextOperation);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        MapOperation<T, R> nextOperation = new MapOperation<>(function);
        operations.get(operations.size() - 1).next(nextOperation);
        operations.add(nextOperation);
        return (Stream<R>) this;
    }

    public Pipeline collectTo(List<T> list) {
        target = new OutputTarget<>(list);
        return new Pipeline<>(operations, target);
    }

    public Stream<T> on(LambdaContainer lambdaContainer) {
        this.lambdaContainer = lambdaContainer;
        return this;
    }
}
