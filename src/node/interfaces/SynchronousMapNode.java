package node.interfaces;

import message.DataMessage;

import java.io.Serializable;

public interface SynchronousMapNode <T extends Serializable, R extends Serializable> extends Node<R>, Target<T>, Relay<R> {

    DataMessage<R> apply(DataMessage<T> dataMessage);

}
