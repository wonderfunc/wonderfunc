package node.interfaces;

import marshall.Marshallable;
import functionRepository.AsynchronousFunction;

public interface FunctionRepository {
    <T extends Marshallable> AsynchronousFunction create(String functionID, Class<T> marshable);
}
