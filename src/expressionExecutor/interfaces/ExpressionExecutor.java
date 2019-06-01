package expressionExecutor.interfaces;

import functionRepository.interfaces.Dispatcher;
import message.Message;

import java.io.Serializable;
import java.util.function.Function;

public interface ExpressionExecutor<T extends Serializable, O extends Serializable>  {
    ExpressionExecutor<T, O> addDispatcher(Dispatcher<O> dispatcher);
    void add(Message<T> inputMessage);
    ExpressionExecutor<T, O> function(Function<T, O> function);
}
