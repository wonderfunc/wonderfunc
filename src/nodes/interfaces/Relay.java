package nodes.interfaces;

import message.Message;

import java.io.Serializable;
import java.util.List;

public interface Relay <T extends Serializable> {
    void relay(Message<T> message);
}
