package node.interfaces;

import java.io.Serializable;

public interface AsynchronousMapNode<T extends Serializable, R extends Serializable> extends Node<R>, Target<T>, Relay<R>  {

}
