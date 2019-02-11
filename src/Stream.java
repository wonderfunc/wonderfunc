import containers.FilterUnit;
import containers.MapUnit;
import containers.SourceUnit;
import interfaces.Unit;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<I extends Serializable> {
    private final Unit<I> unit;
    private final Unit sourceUnit;

    private Stream(Unit<I> unit, Unit sourceUnit) {
        this.unit = unit;
        this.sourceUnit = sourceUnit;
    }

    public static <I extends Serializable> Stream<I> source(Iterable<I> iterable) {
        Unit<I> unit = relayerOf(iterable);
        return new Stream<>(unit, unit);
    }

    public <O extends Serializable> Stream<O> map(Function<I, O> function) {
        MapUnit<I, O> container = new MapUnit<>(function);
        unit.next(container);
        return new Stream<>(container, this.sourceUnit);
    }


    public Stream<I> filter(Predicate<I> predicate) {
        FilterUnit<I> container = new FilterUnit<>(predicate);
        unit.next(container);
        return new Stream<>(container, this.sourceUnit);
    }

    private static <I extends Serializable> Unit<I> relayerOf(Iterable<I> iterable) {
        return new SourceUnit<>(iterable);
    }
}
