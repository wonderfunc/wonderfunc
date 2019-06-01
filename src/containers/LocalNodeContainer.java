package containers;

import node.interfaces.FilterNode;
import node.local.*;
import node.interfaces.CollectNode;
import stream.OutputTarget;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public class LocalNodeContainer implements NodeContainer {
    @Override
    public <T extends Serializable> FilterNode<T> createNodeFor(Predicate<T> predicate) {
        return new LocalFilterNode<>(predicate);
    }

    @Override
    public <T extends Serializable, R extends Serializable> LocalMapNode<T, R> createNodeFor(Function<T, R> function) {
        return new LocalMapNode<>(function);
    }

    @Override
    public <T extends Serializable> CollectNode<T> createNodeFor(OutputTarget<T> outputTarget) {
        return new LocalCollectNode(outputTarget);
    }

}
