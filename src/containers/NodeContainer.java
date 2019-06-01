package containers;

import node.interfaces.CollectNode;
import node.interfaces.FilterNode;
import node.local.LocalMapNode;
import stream.OutputTarget;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public interface NodeContainer {

    <T extends Serializable> FilterNode<T> createNodeFor(Predicate<T> predicate);

    <T extends Serializable, R extends Serializable> LocalMapNode<T, R> createNodeFor(Function<T, R> function);

    <T extends Serializable> CollectNode<T> createNodeFor(OutputTarget<T> outputTarget);
}
