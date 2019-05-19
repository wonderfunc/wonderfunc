package functionRepository.interfaces;

import marshall.Marshallable;
import functionRepository.algorithmia.AlgorithmiaAsynchronousFunction;

public interface FunctionRepository {
    <T extends Marshallable> AlgorithmiaAsynchronousFunction create(String functionID, Class<T> marshable);
}
