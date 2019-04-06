package repositories;

import repositories.algorithmia.AsynchronousFunction;

public interface FunctionRepository {
    AsynchronousFunction create(String functionID);
}
