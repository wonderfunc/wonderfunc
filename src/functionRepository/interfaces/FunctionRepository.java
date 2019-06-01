package functionRepository.interfaces;

import marshall.Marshalling;

import java.io.Serializable;
import java.util.function.Function;

public interface FunctionRepository {
    <T extends Serializable, O extends Serializable> Function<T, O> function(String functionID);
}
