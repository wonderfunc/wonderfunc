package operations;

import java.io.Serializable;
import java.util.function.Predicate;

public class FilterOperation<T extends Serializable> implements Operation {

    private Predicate<T> predicate;

    public FilterOperation(Predicate<T> predicate) {
        this.predicate = predicate;
    }

}
