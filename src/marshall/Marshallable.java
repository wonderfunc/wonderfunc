package marshall;

import java.io.Serializable;

public interface Marshallable<T extends Serializable> {

    String marshall(T messageData);
    T unmarshall(String output);
}
