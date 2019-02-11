import containers.FilterUnit;
import containers.MapUnit;
import containers.SourceUnit;
import interfaces.Unit;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<I extends Serializable> {
    private final Unit<I> unit;

    private Stream(Unit<I> unit) {
        this.unit = unit;
    }

    public static <I extends Serializable> Stream<I> source(Iterable<I> iterable) {
        return new Stream<>(relayerOf(iterable));
    }

    public <O extends Serializable> Stream<O> map(Function<I, O> function) {
        MapUnit<I, O> container = new MapUnit<>(function);
        unit.next(container);
        return new Stream<>(container);
    }


    public Stream<I> filter(Predicate<I> predicate) {
        FilterUnit<I> container = new FilterUnit<>(predicate);
        unit.next(container);
        return new Stream<>(container);
    }

    private static <I extends Serializable> Unit<I> relayerOf(Iterable<I> iterable) {
        return new SourceUnit<>(iterable);
    }
}
