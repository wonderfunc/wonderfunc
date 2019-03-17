package operations.interfaces;

import message.Message;

import java.io.Serializable;

public interface Relay <T extends Serializable> {
    void relay(Message<T> data);
}
