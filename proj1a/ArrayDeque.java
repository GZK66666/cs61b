public class ArrayDeque<T> {
    private T[] Ts;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        Ts = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private int oneMinus(int index){
        index = index - 1;
        if(index < 0){
            index = Ts.length - 1;
        }
        return index;
    }

    private int onePlus(int index){
        index = index + 1;
        if(index == Ts.length){
            index = 0;
        }
        return index;
    }

    private void resize(int capacity){
        T[] newTs = (T[]) new Object[capacity];

        int currentFirst = onePlus(nextFirst);
        int currentLast = oneMinus(nextLast);

        if (currentFirst < currentLast) {
            int length = currentLast - currentFirst + 1;
            System.arraycopy(Ts , currentFirst , newTs , 0 , length);
            nextFirst = newTs.length - 1;
            nextLast = length;
        }else {
            int lengthFirst = Ts.length - currentFirst;
            System.arraycopy(Ts , 0 , newTs , 0 , nextLast);
            System.arraycopy(Ts , currentFirst , newTs , capacity - lengthFirst , lengthFirst);
            nextFirst = capacity - lengthFirst - 1;
        }
        Ts = newTs;
    }

    public void addFirst(T t) {
        Ts[nextFirst] = t;
        nextFirst = oneMinus(nextFirst);
        size += 1;

        if (size == Ts.length) {
            //resize
            resize(Ts.length * 2);
        }
    }

    public void addLast(T t) {
        Ts[nextLast] = t;
        nextLast = onePlus(nextLast);
        size += 1;

        if (size == Ts.length) {
            //resize
            resize(Ts.length * 2);
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
            if (first == Ts.length) {
                first = 0;
            }
            System.out.print(Ts[first] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int currentFirst = onePlus(nextFirst);
        T t = Ts[currentFirst];
        size -= 1;
        nextFirst = currentFirst;

        double ratio = (double)size / Ts.length;
        if (ratio < 0.25 && Ts.length > 16) {
            //resize
            resize(Ts.length / 2);
        }

        return t;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int currentLast = oneMinus(nextLast);
        T t = Ts[currentLast];
        size -= 1;
        nextLast = currentLast;

        double ratio = (double)size / Ts.length;
        if (ratio < 0.25 && Ts.length >= 16) {
            //resize
            resize(Ts.length / 2);
        }

        return t;
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        if (index >= size){
            return null;
        }

        return Ts[(nextFirst + index + 1) % Ts.length];
    }
}
