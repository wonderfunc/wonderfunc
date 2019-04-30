package containers;

import nodes.interfaces.AsynchronousMapNode;
import nodes.interfaces.CollectNode;
import nodes.interfaces.FilterNode;
import nodes.interfaces.SynchronousMapNode;
import repositories.AsynchronousFunction;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public class Hadoop implements LambdaContainer {

    public Hadoop(String connectionString) {

    }

    @Override
    public FilterNode createNodeFor(Predicate predicate) {
        return null;
    }

    @Override
    public SynchronousMapNode createNodeFor(Function function) {
        return null;
    }

    @Override
    public AsynchronousMapNode createNodeFor(AsynchronousFunction function) {
        return null;
    }

    @Override
    public CollectNode createNodeFor(OutputTarget outputTarget) {
        return null;
    }
}
