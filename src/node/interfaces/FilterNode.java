package node.interfaces;

import message.DataMessage;

import java.io.Serializable;

public interface FilterNode<T extends Serializable> extends Node<T>, Target<T>, Relay<T> {

    boolean test(DataMessage<T> dataMessage);

}
