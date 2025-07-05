package UI;

public class Reference<T> {
    private T item;

    public Reference(T item) {
        this.item = item;
    }

    public Reference() {
        item = null;
    }

    public T get() {
        return item;
    }

    public void set(T newItem) {
        item = newItem;
    }


}
