package node;

import message.DataMessage;
import message.EndOfStreamMessage;
import node.interfaces.Node;
import node.interfaces.Target;

import java.io.Serializable;
import java.util.List;

public class SourceNode<T extends Serializable> implements Node<T> {

    private Target<T> target;
    private final List<T> inputList;

    public SourceNode(List<T> nodes) {
        this.inputList = nodes;
    }

    public void relayAll() {
        for (T inputData : inputList) target.push(new DataMessage<>(inputData));
        target.push(new EndOfStreamMessage());
    }

    @Override
    public void next(Target<T> target) {
        this.target = target;
    }
}
