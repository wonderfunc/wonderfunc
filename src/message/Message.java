package message;

import java.io.Serializable;

public class Message<T extends Serializable> {

    private final T data;
    private final MessageType type;

    public Message(T data, MessageType type) {
        this.data = data;
        this.type = type;
    }

    public Message(MessageType type) {
        this(null, type);
    }

    public T data() {
        return data;
    }

    public MessageType type() {
        return type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data=" + data +
                ", type=" + type +
                '}';
    }
}
