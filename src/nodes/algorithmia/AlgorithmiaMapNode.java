package nodes.algorithmia;

import message.Message;
import nodes.interfaces.AsynchronousMapNode;
import repositories.algorithmia.AlgorithmiaFunction;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public abstract class AlgorithmiaMapNode <T extends Serializable, R extends Serializable> extends AsynchronousMapNode<T, R> {

    public AlgorithmiaMapNode(Function function, int cacheSize) {
        super(function, cacheSize);
    }

    public AlgorithmiaMapNode(Function function) {
        super(function);
    }

    /*@Override
    protected String serialize(List<Message> messages) {
        return null;
    }

    @Override
    protected List<Message> deserialize(String output) {
        return null;
    }*/
}
