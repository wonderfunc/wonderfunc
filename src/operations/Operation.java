package operations;

import java.io.Serializable;

public interface Operation<T extends Serializable> {
    void put(T data);
}
