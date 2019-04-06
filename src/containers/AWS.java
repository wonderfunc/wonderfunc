package containers;

import nodes.interfaces.CollectNode;
import nodes.interfaces.FilterNode;
import nodes.interfaces.SynchronousMapNode;
import stream.OutputTarget;

import java.util.function.Function;
import java.util.function.Predicate;

public class AWS implements LambdaContainer {

    public AWS(String connectionString) {

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
    public CollectNode createNodeFor(OutputTarget outputTarget) {
        return null;
    }
}
