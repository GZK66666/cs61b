public class SList<Type> {
    public Type item;
    public SList next , pre;

    public SList(Type i , SList n , SList p){
        item = i;
        next = n;
        pre = p;
    }

    public Type get(int index){
        if(index == 0){
            return item;
        }else{
            return (Type) next.get(index - 1);
        }
    }
}
