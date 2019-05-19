package node.interfaces;

import java.io.Serializable;

public interface SourceNode <T extends Serializable> extends Node<T>, Relay<T> {
    void relayAll();
}
