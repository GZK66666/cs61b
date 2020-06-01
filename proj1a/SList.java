public class SList<T> {
    public T T;
    public SList next;
    public SList pre;

    public SList(T i , SList n , SList p) {
        T = i;
        next = n;
        pre = p;
    }

    public T get(int index) {
        if (index == 0) {
            return T;
        }else {
            return (T) next.get(index - 1);
        }
    }
}
