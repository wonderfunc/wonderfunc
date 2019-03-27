package nodes;

import message.DataMessage;
import nodes.interfaces.Node;
import nodes.interfaces.Target;

import java.io.Serializable;
import java.util.List;

public class SourceNode<T extends Serializable> implements Node<T> {

    private Target<T> target;
    private final List<T> inputList;

    public SourceNode(List<T> nodes) {
        this.inputList = nodes;
    }

    public void relayAll() {
        for (T inputData : inputList) target.put(new DataMessage<>(inputData));
    }

    @Override
    public void next(Target<T> target) {
        this.target = target;
    }
}
