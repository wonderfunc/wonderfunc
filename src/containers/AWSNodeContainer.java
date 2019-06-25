package containers;

import node.aws.AWSCollectNode;
import node.aws.AWSFilterNode;
import node.aws.AWSMapNode;
import node.interfaces.CollectNode;
import node.interfaces.FilterNode;
import node.interfaces.MapNode;
import stream.OutputTarget;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public class AWSNodeContainer implements NodeContainer {

    private final String ACCESS_KEY_ID;
    private final String SECRET_ACCESS_KEY;

    public AWSNodeContainer(String ACCESS_KEY_ID, String SECRET_ACCESS_KEY) {
        this.ACCESS_KEY_ID = ACCESS_KEY_ID;
        this.SECRET_ACCESS_KEY = SECRET_ACCESS_KEY;
    }

    @Override
    public <T extends Serializable> FilterNode<T> createNodeFor(Predicate<T> predicate) {
        return new AWSFilterNode<>(predicate);
    }

    @Override
    public <T extends Serializable, R extends Serializable> MapNode<T, R> createNodeFor(Function<T, R> function) {
        return new AWSMapNode<>(function);
    }

    @Override
    public <T extends Serializable> CollectNode<T> createNodeFor(OutputTarget<T> outputTarget) {
        return new AWSCollectNode(outputTarget);
    }
}
