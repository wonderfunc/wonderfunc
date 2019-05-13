package node.interfaces;

import java.io.Serializable;

public interface Node<T extends Serializable> {

    void next(Target<T> target);

}
