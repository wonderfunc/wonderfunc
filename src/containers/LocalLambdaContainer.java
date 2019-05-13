package containers;

import node.AsynchronousMapNode;
import node.interfaces.CollectNode;
import node.interfaces.SynchronousMapNode;
import node.local.LocalCollectNode;
import node.local.LocalFilterNode;
import node.local.LocalMapNode;
import functionRepository.AsynchronousFunction;
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
        return new LocalMapNode<>(function);
    }

    @Override
    public AsynchronousMapNode createNodeFor(AsynchronousFunction function) {
        return new AsynchronousMapNode(function);
    }

    @Override
    public CollectNode createNodeFor(OutputTarget outputTarget) {
        return new LocalCollectNode(outputTarget);
    }
}
