package stream;

import containers.LocalNodeContainer;
import containers.NodeContainer;
import expressionExecutor.interfaces.ExpressionExecutor;
import node.interfaces.FilterNode;
import node.interfaces.MapNode;
import node.interfaces.Node;
import node.interfaces.Target;
import node.local.LocalSourceNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<T extends Serializable> {
    private List<Node> nodes = new ArrayList<>();
    private NodeContainer currentNodeContainer;

    public Stream(List<T> list) {
        this.nodes.add(new LocalSourceNode(list));
        this.currentNodeContainer = new LocalNodeContainer();
    }

    public Stream<T> filter(Predicate<T> predicate) {
        register(currentNodeContainer.createNodeFor(predicate));
        return this;
    }

    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        register(currentNodeContainer.createNodeFor(function));
        return (Stream<R>) this;
    }

    public Pipeline collectTo(List<T> list) {
        chain(currentNodeContainer.createNodeFor(outputOf(list)));
        return new Pipeline<>(nodes);
    }

    private void register(FilterNode node) {
        chain(node);
        append(node);
    }

    private void register(MapNode node) {
        chain(node);
        append(node);
    }

    private void chain(Target target) {
        lastNode().next(target);
    }

    private void append(Node node) {
        nodes.add(node);
    }

    private Node lastNode() {
        return nodes.get(nodes.size() - 1);
    }

    private OutputTarget outputOf(List<T> list) {
        return new OutputTarget<>(list);
    }

    public Stream<T> on(NodeContainer nodeContainer) {
        currentNodeContainer = nodeContainer;
        return this;
    }

    public <R extends Serializable> Stream<R> with(ExpressionExecutor<T, R> expressionExecutor) {
        Node lastNode = nodes.get(nodes.size() - 1);
        if (lastNode instanceof MapNode) {
            MapNode mapNode = (MapNode) lastNode;
            mapNode.setExpressionExecutor(expressionExecutor.function(mapNode.function()));
        }
        return (Stream<R>)this;
    }

}
