import containers.FilterUnit;
import containers.MapUnit;
import containers.SourceUnit;
import interfaces.Unit;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<I extends Serializable> {
    private final Unit<I> unit;
    private static Unit sourceUnit;

    private Stream(Unit<I> unit) {
        this.unit = unit;
    }

    public static <I extends Serializable> Stream<I> source(Iterable<I> iterable) {

        Unit<I> sourceUnit = relayerOf(iterable);
        Stream.initSourceUnit(sourceUnit);
        return new Stream<>(sourceUnit);
    }

    public <O extends Serializable> Stream<O> map(Function<I, O> function) {
        MapUnit<I, O> unit = new MapUnit<>(function);
        this.unit.next(unit);
        return new Stream<>(unit);
    }


    public Stream<I> filter(Predicate<I> predicate) {
        FilterUnit<I> unit = new FilterUnit<>(predicate);
        this.unit.next(unit);
        return new Stream<>(unit);
    }

    private static <I extends Serializable> Unit<I> relayerOf(Iterable<I> iterable) {
        return new SourceUnit<>(iterable);
    }

    private static void initSourceUnit(Unit unit) {
        Stream.sourceUnit = unit;
    }

    public static Unit sourceUnit() {
        return Stream.sourceUnit;
    }
}
