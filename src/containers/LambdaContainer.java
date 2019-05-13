package containers;

import node.AsynchronousMapNode;
import node.interfaces.CollectNode;
import node.interfaces.FilterNode;
import node.interfaces.SynchronousMapNode;
import functionRepository.AsynchronousFunction;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public interface LambdaContainer {

    FilterNode createNodeFor(Predicate predicate);

    SynchronousMapNode createNodeFor(Function function);

    AsynchronousMapNode createNodeFor(AsynchronousFunction function);

    CollectNode createNodeFor(OutputTarget outputTarget);
}
