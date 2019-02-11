package interfaces;

import java.io.Serializable;

public interface Target<T extends Serializable> {
    void put(T data);
}
