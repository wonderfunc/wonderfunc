package stream;

import containers.LambdaContainer;
import containers.LocalLambdaContainer;
import nodes.interfaces.*;
import nodes.SourceNode;
import repositories.algorithmia.AlgorithmiaFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<T extends Serializable> {
    private List<Node> nodes = new ArrayList<>();
    private LambdaContainer currentLambdaContainer;

    public Stream(List<T> list) {
        this.nodes.add(new SourceNode(list));
        this.currentLambdaContainer = new LocalLambdaContainer();
    }

    public Stream<T> filter(Predicate<T> predicate) {
        register(currentLambdaContainer.createNodeFor(predicate));
        return this;
    }

    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        register(currentLambdaContainer.createNodeFor(function));
        return (Stream<R>) this;
    }

    public Pipeline collectTo(List<T> list) {
        chain(currentLambdaContainer.createNodeFor(outputOf(list)));
        return new Pipeline<>(nodes);
    }

    private void register(FilterNode node) {
        chain(node);
        append(node);
    }

    private void register(SynchronousMapNode node) {
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

    public Stream<T> on(LambdaContainer lambdaContainer) {
        currentLambdaContainer = lambdaContainer;
        return this;
    }
}