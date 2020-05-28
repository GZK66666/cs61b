public class ArrayDeque<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private void resize_bigger() {
        T[] items2 = (T[]) new Object[items.length * 2];
        int first = nextFirst + 1;
        int index = 1;
        if (first == items.length){
            first = 0;
        }
        for (int i = 0; i < size; i++) {
            items2[index++] = items[first];
            first = (first + 1) % items.length;
        }
        items = items2;
        nextFirst = 0;
        nextLast = index;
    }

    private void resize_smaller() {
        T[] items2 = (T[]) new Object[items.length/4 + 2];
        int first = nextFirst + 1;
        int index = 1;
        if (first == items.length){
            first = 0;
        }
        for (int i = 0; i < size; i++) {
            items2[index++] = items[first];
            first = (first + 1) % items.length;
        }
        items = items2;
        nextFirst = 0;
        nextLast = index;
    }

    public void addFirst(T t) {
        if (size == items.length) {
            //resize
            resize_bigger();
        }
        items[nextFirst] = t;
        nextFirst = ((nextFirst - 1) + items.length) % items.length;
        size += 1;
    }

    public void addLast(T t) {
        if (size == items.length) {
            //resize
            resize_bigger();
        }
        items[nextLast] = t;
        nextLast = (nextLast + 1) % items.length;
        size += 1;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public void printDeque() {
        if (size == 0) {
            System.out.println();
        }
        int first = nextFirst + 1;
        while (first != nextLast) {
            if (first == items.length) {
                first = 0;
            }
            System.out.print(items[first] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        int first = nextFirst + 1;

        if (first == items.length) {
            first = 0;
        }

        nextFirst = first;
        size -= 1;

        double ratio = size / items.length;
        if (ratio < 0.25 && items.length >= 16) {
            //resize
            resize_smaller();
        }

        return items[first];
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int last = nextLast - 1;
        if (last < 0) {
            last = items.length - 1;
        }

        nextLast = last;
        size -= 1;

        double ratio = size / items.length;
        if (ratio < 0.25 && items.length >= 16) {
            //resize
            resize_smaller();
        }

        return items[last];
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        return items[(nextFirst + index + 1) % items.length];
    }
}
