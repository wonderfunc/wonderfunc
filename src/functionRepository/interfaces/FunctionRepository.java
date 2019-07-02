package functionRepository.interfaces;


import marshall.Marshalling;

import java.io.Serializable;
import java.util.function.Function;

public interface FunctionRepository {
    <T extends Serializable, R extends Serializable> Function<T, R> function(String functionID, Marshalling marshalling);
}
