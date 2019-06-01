package node.local;

import expressionExecutor.LocalExpressionExecutor;
import expressionExecutor.interfaces.ExpressionExecutor;
import message.Message;
import node.interfaces.MapNode;
import node.interfaces.Target;

import java.io.Serializable;
import java.util.function.Function;

public class LocalMapNode<T extends Serializable, R extends Serializable> implements MapNode<T, R> {

    private final Function<T, R> function;
    private ExpressionExecutor<T, R> expressionExecutor;
    private Target<R> target;

    public LocalMapNode(Function<T, R> function) {
        this.function = function;
        this.expressionExecutor = new LocalExpressionExecutor<T, R>().addDispatcher(this).function(function);
    }

    @Override
    public void next(Target<R> target) {
        this.target = target;
    }

    @Override
    public void relay(Message<R> message) {
        target.push(message);
    }

    @Override
    public void push(Message<T> message) {
        expressionExecutor.add(message);
    }

    @Override
    public void dispatch(Message<R> output) {
        relay(output);
    }

    @Override
    public void setExpressionExecutor(ExpressionExecutor<T, R> expressionExecutor) {
        this.expressionExecutor = expressionExecutor.addDispatcher(this);
    }

    @Override
    public Function<T, R> function() {
        return function;
    }
}