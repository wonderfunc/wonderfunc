package stream;

import java.io.Serializable;
import java.util.List;

public class OutputTarget<T extends Serializable> {
    private List<T> list;

    public OutputTarget(List<T> list) {
        this.list = list;
    }

    public void add(T data) {
        list.add(data);
    }
}
