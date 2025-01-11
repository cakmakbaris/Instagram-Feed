public class MyListNode<K, V> {
    public MyListNode<K, V> next;
    private K key;

    private V value;

    MyListNode(K key, V value){
        this.key = key;
        this.value = value;
    }


    public V getValue(){
        return value;
    }

    public K getKey(){
        return key;
    }
}
