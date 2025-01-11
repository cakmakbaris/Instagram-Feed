
public class MyLinkedList<K, V> {
    public MyListNode<K, V> head;
    public MyListNode<K, V> tail;

    int size = 0;


    MyLinkedList(){
        // No-arg constructor
    }

    /**
     * Given a Key, Value pair insert it into LL if it is not in the LL
     * @param key Key
     * @param value Value
     * @return True if insertion is successful, false otherwise
     */
    public boolean insert(K key, V value){
        if (containsKey(key)) // Key already exists
            return false;
        MyListNode<K, V> newNode = new MyListNode<>(key, value); // Node that we will add
        // Case1 - LL is empty before insertion
        if (head == null){
            // head and tail both point to same node since there is only 1 node
            head = newNode;
            tail = head;
        }
        // Case2 - LL is not empty before insertion
        else{
            tail.next = newNode; // Add node to end
            tail = newNode; // Change the tail to newNode
        }
        size++;
        return true;
    }

    /**
     * Given a Key remove node with corresponding Key if it exists
     * @param key Key
     * @return true if deletion is successful, false otherwise
     */
    public boolean delete(K key){
        if (head == null) // Empty LL
            return false;

        // Case1 - removing head node
        if (head.getKey().equals(key)){
            head = head.next; // Java garbage collector automatically deletes the non-pointed node
            size--;
            // Case1a - if head becomes null, set tail to null also (before removal there was 1 node)
            if (head == null) {
                tail = null;
            }
            return true;
        }

        // Case2 - removing non-head node
        MyListNode<K, V> p1 = head;
        MyListNode<K, V> p2 = head.next;
        while (p2 != null && !p2.getKey().equals(key)){
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p2 == null) // Key not found
            return false;
        p1.next = p1.next.next; // Remove the node in between
        size--;
        if (p2 == tail) // If we are deleting tail node, make p1 the new tail
            tail = p1;

        return true;

    }

    /**
     * Finds out if LL contains the key Key
     * @param key Key
     * @return True if there exists a node with this key, false otherwise
     */
    public boolean containsKey(K key){
        MyListNode<K, V> current = head;
        while (current != null){
            if (current.getKey().equals(key))
                return true;
            current = current.next;
        }
        return false;
    }


}
