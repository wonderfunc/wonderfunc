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
    public LocalFilterNode getFilterNodeFrom(Predicate predicate) {
        return new LocalFilterNode<>(predicate);
    }

    @Override
    public LocalMapNode getMapNodeFrom(Function function) {
        return new LocalMapNode<>(function);
    }

    @Override
    public CollectNode getCollectNode(OutputTarget outputTarget) {
        return new LocalCollectNode(outputTarget);
    }
}
