package containers;

import nodes.interfaces.CollectNode;
import nodes.local.LocalCollectNode;
import nodes.local.LocalFilterNode;
import nodes.local.LocalMapNode;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public class LocalLambdaContainer implements LambdaContainer {
    @Override
    public LocalFilterNode createNodeFor(Predicate predicate) {
        return new LocalFilterNode<>(predicate);
    }

    @Override
    public LocalMapNode createNodeFor(Function function) {
        return new LocalMapNode<>(function);
    }

    @Override
    public CollectNode createNodeFor(OutputTarget outputTarget) {
        return new LocalCollectNode(outputTarget);
    }
}
