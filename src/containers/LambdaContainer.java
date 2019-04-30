package containers;

import nodes.interfaces.AsynchronousMapNode;
import nodes.interfaces.CollectNode;
import nodes.interfaces.FilterNode;
import nodes.interfaces.SynchronousMapNode;
import repositories.AsynchronousFunction;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public interface LambdaContainer {

    FilterNode createNodeFor(Predicate predicate);

    SynchronousMapNode createNodeFor(Function function);

    AsynchronousMapNode createNodeFor(AsynchronousFunction function);

    CollectNode createNodeFor(OutputTarget outputTarget);
}
