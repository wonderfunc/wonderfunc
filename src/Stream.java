import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<I extends Serializable> {
    private final Generator<I> generator;

    private Stream(Generator<I> generator) {
        this.generator = generator;
    }

    public static <I extends Serializable> Stream<I> source(Iterable<I> iterable) {
        return new Stream<>(generatorOf(iterable));
    }

    public <O extends Serializable> Stream<O> map(Function<I, O> function) {
        MapContainer<I, O> container = new MapContainer<>(function);
        generator.setTarget(container);
        return new Stream<>(container);
    }


    public Stream<I> filter(Predicate<I> predicate) {
        FilterContainer<I> container = new FilterContainer<>(predicate);
        generator.setTarget(container);
        return new Stream<>(container);
    }

    private static <I extends Serializable> Generator<I> generatorOf(Iterable<I> iterable) {
        return null;
    }
}
