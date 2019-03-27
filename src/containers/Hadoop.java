package containers;

import nodes.interfaces.CollectNode;
import nodes.interfaces.FilterNode;
import nodes.interfaces.MapNode;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public class Hadoop implements LambdaContainer {

    public Hadoop(String connectionString) {

    }

    @Override
    public FilterNode getFilterNodeFrom(Predicate predicate) {
        return null;
    }

    @Override
    public MapNode getMapNodeFrom(Function function) {
        return null;
    }

    @Override
    public CollectNode getCollectNode(OutputTarget outputTarget) {
        return null;
    }
}
