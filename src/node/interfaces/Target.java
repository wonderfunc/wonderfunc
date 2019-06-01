package node.interfaces;

import message.Message;

import java.io.Serializable;

public interface Target<T extends Serializable> {
    void push(Message<T> message);
}
