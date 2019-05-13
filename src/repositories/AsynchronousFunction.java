package repositories;

import java.io.Serializable;
import java.util.function.Function;

public interface AsynchronousFunction <T extends Serializable> extends Function {
    String marshall(T messageData);
    T unmarshall(String messageData);
}
