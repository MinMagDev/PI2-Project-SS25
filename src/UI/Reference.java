package UI;

/**
 * a simple generic class that represents a reference
 * like a one element array
 * @param <T> the type the reference is for
 */

public class Reference<T> {
    private T item;

    public Reference(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }

    public void set(T newItem) {
        item = newItem;
    }
}
