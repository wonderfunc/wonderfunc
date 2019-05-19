package containers;

import node.local.LocalAsynchronousMapNode;
import node.interfaces.CollectNode;
import node.interfaces.SynchronousMapNode;
import node.local.LocalCollectNode;
import node.local.LocalFilterNode;
import node.local.LocalSynchronousMapNode;
import functionRepository.algorithmia.AlgorithmiaAsynchronousFunction;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public class LocalLambdaContainer implements LambdaContainer {
    @Override
    public LocalFilterNode createNodeFor(Predicate predicate) {
        return new LocalFilterNode<>(predicate);
    }

    @Override
    public SynchronousMapNode createNodeFor(Function function) {
        return new LocalSynchronousMapNode<>(function);
    }

    @Override
    public LocalAsynchronousMapNode createNodeFor(AlgorithmiaAsynchronousFunction function) {
        return new LocalAsynchronousMapNode(function);
    }

    @Override
    public CollectNode createNodeFor(OutputTarget outputTarget) {
        return new LocalCollectNode(outputTarget);
    }
}
