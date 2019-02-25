package operations.interfaces;

import message.Message;

import java.io.Serializable;

public interface Target<T extends Serializable> {
    void put(Message<T> data);
}
