public class LinkedListDeque<Item> implements Deque<Item> {
    private SList first_sentinel;
    private SList last_sentinel;
    private int size;

    public LinkedListDeque() {
        first_sentinel = new SList(null , null , null);
        last_sentinel = new SList(null , null , null);
        first_sentinel.next = last_sentinel;
        last_sentinel.pre = first_sentinel;
        size = 0;
    }

    @Override
    public void addFirst(Item i) {
        SList node = new SList(i , first_sentinel.next , first_sentinel);
        first_sentinel.next.pre = node;
        first_sentinel.next = node;
        size += 1;
    }

    @Override
    public void addLast(Item i) {
        SList node = new SList(i , last_sentinel , last_sentinel.pre);
        last_sentinel.pre.next = node;
        last_sentinel.pre = node;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size == 0) {
            System.out.println();
        }else {
            SList p = first_sentinel.next;
            while (p != last_sentinel) {
                System.out.print(p.item+" ");
                p = p.next;
            }
            System.out.println();
        }
    }

    @Override
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }else {
            Item p = (Item) first_sentinel.next.item;
            first_sentinel.next.next.pre = first_sentinel;
            first_sentinel.next = first_sentinel.next.next;
            size -= 1;
            return p;
        }
    }

    @Override
    public Item removeLast() {
        if (size == 0) {
            return null;
        }else {
            Item p = (Item)last_sentinel.pre.item;
            last_sentinel.pre.pre.next = last_sentinel;
            last_sentinel.pre = last_sentinel.pre.pre;
            size -= 1;
            return p;
        }
    }

    @Override
    public Item get(int index) {
        if (index > size-1) {
            return null;
        }
        SList p = first_sentinel.next;
        int i = 0;
        while (i < index) {
            p = p.next;
            i += 1;
        }

        return (Item)p.item;
    }

    public Item getRecursive(int index){
        if (index > size - 1) {
            return null;
        }else {
            return (Item) first_sentinel.next.get(index);
        }
    }
}