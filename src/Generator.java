import java.io.Serializable;

public interface Generator<T> {
    void setTarget(Target<? extends Serializable> target);
}
