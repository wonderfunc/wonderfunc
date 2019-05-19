package containers;

import node.local.LocalAsynchronousMapNode;
import node.interfaces.CollectNode;
import node.interfaces.FilterNode;
import node.interfaces.SynchronousMapNode;
import functionRepository.algorithmia.AlgorithmiaAsynchronousFunction;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public interface LambdaContainer {

    FilterNode createNodeFor(Predicate predicate);

    SynchronousMapNode createNodeFor(Function function);

    LocalAsynchronousMapNode createNodeFor(AlgorithmiaAsynchronousFunction function);

    CollectNode createNodeFor(OutputTarget outputTarget);
}
