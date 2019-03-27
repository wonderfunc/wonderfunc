package stream;

import containers.LambdaContainer;
import containers.LocalLambdaContainer;
import nodes.interfaces.CollectNode;
import nodes.SourceNode;
import nodes.interfaces.FilterNode;
import nodes.interfaces.MapNode;
import nodes.interfaces.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<T extends Serializable> {
    private List<Node> nodes = new ArrayList<>();
    private LambdaContainer lambdaContainer;

    public Stream(List<T> list) {
        nodes.add(new SourceNode(list));
        this.lambdaContainer = new LocalLambdaContainer();
    }

    public Stream<T> filter(Predicate<T> predicate) {
        FilterNode<T> node = lambdaContainer.getFilterNodeFrom(predicate);
        nodes.get(nodes.size() - 1).next(node);
        nodes.add(node);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        MapNode<T, R> node = lambdaContainer.getMapNodeFrom(function);
        nodes.get(nodes.size() - 1).next(node);
        nodes.add(node);
        return (Stream<R>) this;
    }

    public Pipeline collectTo(List<T> list) {
        CollectNode<T> node = lambdaContainer.getCollectNode(new OutputTarget<>(list));
        nodes.get(nodes.size() - 1).next(node);
        //nodes.add(node);

        return new Pipeline<>(nodes);
    }

    public Stream<T> on(LambdaContainer lambdaContainer) {
        this.lambdaContainer = lambdaContainer;
        return this;
    }
}
