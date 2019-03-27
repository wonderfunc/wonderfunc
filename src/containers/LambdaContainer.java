package containers;

import nodes.interfaces.CollectNode;
import nodes.interfaces.FilterNode;
import nodes.interfaces.MapNode;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public interface LambdaContainer {

    FilterNode getFilterNodeFrom(Predicate predicate);

    MapNode getMapNodeFrom(Function function);

    CollectNode getCollectNode(OutputTarget outputTarget);
}
