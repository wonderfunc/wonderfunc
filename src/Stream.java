import connection.Collector;
import units.FilterUnit;
import units.MapUnit;
import units.SourceUnit;
import interfaces.Unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<I extends Serializable> {
    private static Thread collectorThread;
    private final Unit<I> unit;
    private final SourceUnit sourceUnit;

    private Stream(Unit<I> unit, SourceUnit sourceUnit) {
        this.unit = unit;
        this.sourceUnit = sourceUnit;
    }

    public static <I extends Serializable> Stream<I> source(Iterable<I> iterable) {
        Unit<I> unit = relayerOf(iterable);
        startCollector();
        return new Stream<>(unit, (SourceUnit)unit);
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

    public List<I> collect() {
        sourceUnit.relayAll();
        try {
            Stream.collectorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static <I extends Serializable> SourceUnit<I> relayerOf(Iterable<I> iterable) {
        return new SourceUnit<>(iterable);
    }

    private static void startCollector() {
        Thread thread = new Thread(new Collector());
        Stream.collectorThread = thread;
        thread.start();
    }
}
