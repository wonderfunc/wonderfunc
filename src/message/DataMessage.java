package message;

import java.io.Serializable;

public class DataMessage<T extends Serializable> implements Message {

    private final T data;

    public DataMessage(T data) {
        this.data = data;
    }

    public T data() {
        return data;
    }

}
