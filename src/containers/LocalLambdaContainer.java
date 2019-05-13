package containers;

import nodes.interfaces.AsynchronousMapNode;
import nodes.interfaces.CollectNode;
import nodes.interfaces.SynchronousMapNode;
import nodes.local.LocalCollectNode;
import nodes.local.LocalFilterNode;
import nodes.local.LocalMapNode;
import repositories.AsynchronousFunction;
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
