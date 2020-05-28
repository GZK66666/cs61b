public class SList<T> {
    public T item;
    public SList next;
    public SList pre;

    public SList(T i , SList n , SList p) {
        item = i;
        next = n;
        pre = p;
    }

    public T get(int index) {
        if (index == 0) {
            return item;
        }else {
            return (T) next.get(index - 1);
        }
    }
}
