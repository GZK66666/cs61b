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

    private int oneMinus(int index){
        index = index - 1;
        if(index < 0){
            index = items.length - 1;
        }
        return index;
    }

    private int onePlus(int index){
        index = index + 1;
        if(index == items.length){
            index = 0;
        }
        return index;
    }

    private void resize(int capacity){
        T[] newitems = (T[]) new Object[capacity];

        int currentFirst = onePlus(nextFirst);
        int currentLast = oneMinus(nextLast);

        if (nextFirst < nextLast) {
            int length = currentLast - currentFirst + 1;
            System.arraycopy(items , currentFirst , newitems , 0 , length);
            nextFirst = newitems.length - 1;
            nextLast = length;
        }else {
            int lengthFirst = items.length - currentFirst;
            System.arraycopy(items , 0 , newitems , 0 , nextLast);
            System.arraycopy(items , currentFirst , newitems , capacity - lengthFirst , lengthFirst);
            nextFirst = capacity - lengthFirst - 1;
        }
        items = newitems;
    }

    public void addFirst(T t) {
        items[nextFirst] = t;
        nextFirst = oneMinus(nextFirst);
        size += 1;

        if (size == items.length) {
            //resize
            resize(items.length * 2);
        }
    }

    public void addLast(T t) {
        items[nextLast] = t;
        nextLast = onePlus(nextLast);
        size += 1;

        if (size == items.length) {
            //resize
            resize(items.length * 2);
        }
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
        if (isEmpty()) {
            return null;
        }

        int currentFirst = onePlus(nextFirst);
        T t = items[currentFirst];

        double ratio = size / items.length;
        if (ratio < 0.25 && items.length > 16) {
            //resize
            resize(size / 2);
        }

        size -= 1;
        nextFirst = currentFirst;

        return t;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int currentLast = oneMinus(nextLast);
        T t = items[currentLast];

        double ratio = size / items.length;
        if (ratio < 0.25 && items.length >= 16) {
            //resize
            resize(size / 2);
        }

        size -= 1;
        nextLast = currentLast;

        return t;
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        return items[(nextFirst + index + 1) % items.length];
    }
}
