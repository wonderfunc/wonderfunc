package marshall;

import java.io.Serializable;

public interface Marshalling<T extends Serializable, O extends Serializable> {

    String marshall(T messageData);
    O unmarshall(String output);
}
