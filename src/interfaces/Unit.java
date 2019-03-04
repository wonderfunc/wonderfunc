package interfaces;

import java.io.Serializable;

public interface Unit<T extends Serializable> {
    void next(Target<T> target);
}
