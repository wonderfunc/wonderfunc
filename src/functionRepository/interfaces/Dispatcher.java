package functionRepository.interfaces;

import message.Message;

import java.io.Serializable;

public interface Dispatcher<T extends Serializable> {
    void dispatch(Message<T> output);
}
