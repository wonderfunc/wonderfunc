package operations.interfaces;

import java.io.Serializable;

public interface Operation<T extends Serializable> {
    void next(Target<T> target);
}
