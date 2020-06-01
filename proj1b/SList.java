public class SList<Item> {
    public Item item;
    public SList next;
    public SList pre;

    public SList(Item i , SList n , SList p) {
        item = i;
        next = n;
        pre = p;
    }

    public Item get(int index) {
        if (index == 0) {
            return item;
        }else {
            return (Item) next.get(index - 1);
        }
    }
}
