package expressionExecutor;

import expressionExecutor.interfaces.ExpressionExecutor;
import functionRepository.interfaces.Dispatcher;
import message.DataMessage;
import message.EndOfStreamMessage;
import message.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LocalExpressionExecutor<T extends Serializable, R extends Serializable> implements ExpressionExecutor<T, R> {

    private Function<T, R> function;
    private List<Dispatcher<R>> dispatchers;

    public LocalExpressionExecutor() {
        dispatchers = new ArrayList<>();
    }

    @Override
    public ExpressionExecutor<T, R> addDispatcher(Dispatcher<R> dispatcher) {
        dispatchers.add(dispatcher);
        return this;
    }

    @Override
    public void add(Message<T> inputMessage) {
        if (inputMessage instanceof DataMessage) {
            R output = function.apply(((DataMessage<T>) inputMessage).data());
            dispatchers.forEach(d -> d.dispatch(new DataMessage<R>(output)));
        } else {
            dispatchers.forEach(d -> d.dispatch(new EndOfStreamMessage()));
        }
    }

    @Override
    public ExpressionExecutor function(Function function) {
        this.function = function;
        return this;
    }

}
