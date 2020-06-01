public class ArrayDeque<Item> {
    private Item[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
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
        Item[] newitems = (Item[]) new Object[capacity];

        int currentFirst = onePlus(nextFirst);
        int currentLast = oneMinus(nextLast);

        if (currentFirst < currentLast) {
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

    public void addFirst(Item t) {
        items[nextFirst] = t;
        nextFirst = oneMinus(nextFirst);
        size += 1;

        if (size == items.length) {
            //resize
            resize(items.length * 2);
        }
    }

    public void addLast(Item t) {
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

    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int currentFirst = onePlus(nextFirst);
        Item t = items[currentFirst];
        size -= 1;
        nextFirst = currentFirst;

        double ratio = (double)size / items.length;
        if (ratio < 0.25 && items.length > 16) {
            //resize
            resize(size / 2);
        }

        return t;
    }

    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }

        int currentLast = oneMinus(nextLast);
        Item t = items[currentLast];
        size -= 1;
        nextLast = currentLast;

        double ratio = (double)size / items.length;
        if (ratio < 0.25 && items.length >= 16) {
            //resize
            resize(size / 2);
        }

        return t;
    }

    public int size() {
        return size;
    }

    public Item get(int index) {
        if (index >= size){
            return null;
        }

        return items[(nextFirst + index + 1) % items.length];
    }
}
