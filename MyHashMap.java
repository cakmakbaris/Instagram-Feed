import java.util.ArrayList;

public class MyHashMap<K, V> {

    private MyLinkedList<K, V>[] hashTable; // Array used to store Key, Value pairs
    private int size; // Num of Key, Value pairs in hashTable

    private int capacity; // Array size of hashTable

    private final int INITIAL_CAPACITY = 11; // Selected a prime number

    /**
     * No-arg constructor
     * Initializes HashMap with initial size INITIAL_CAPACITY
     */
    MyHashMap(){
        hashTable = new MyLinkedList[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) // Initialize linked lists for every index
            hashTable[i] = new MyLinkedList<>();
        this.size = 0;
        capacity = INITIAL_CAPACITY;
    }

    /**
     * Given the Key, finds the hashCode of the Key and takes its mode with respect to capacity of HashMap and return its positive value
     * @param key Key
     * @return Hash value of Key
     */
    private int hash(K key){
        int hash = key.hashCode() % capacity;
        if (hash < 0) // If it is negative, make it positive
            return hash + capacity;
        return hash;
    }

    /**
     * Given a Key and its Value, insert Key,Value pair
     * @param key Key of type K
     * @param value Value of type V
     * @return true if insert is successful, false otherwise
     */
    public boolean insert(K key, V value){
        if ((double)size / capacity >= 0.7) // If load factor is greater or equal to than 0.7, rehash
            rehash();
        int index = hash(key); // Key will be inserted to this index
        MyLinkedList<K, V> cluster = hashTable[index]; // Cluster of Keys hashed to this index
        if (cluster.insert(key, value)){ // If insertion is successful, increment size and return
            size++;
            return true;
        }
        return false; // Duplicate elements are not inserted
    }

    /**
     * Given a Key, remove the Key,Value pair from the HashMap
     * @param key Key
     * @return true if remove is successful, false otherwise
     */
    public boolean remove(K key){
        int index = hash(key); // Key will be removed from this index
        MyLinkedList<K, V> cluster = hashTable[index]; // Cluster of Keys hashed to this index
        if (cluster.delete(key)) { // If deletion is successful, decrement size and return
            size--;
            return true;
        }
        return false; // Key does not exist
    }


    /**
     * Given a Key, return its corresponding Value
     * @param key Key
     * @return Value of the corresponding Key, null if key does not exist
     */
    public V getValue(K key){
        int index = hash(key); // Key is hashed to this index
        MyLinkedList<K, V> cluster = hashTable[index]; // Cluster of Keys hashed to this index
        MyListNode<K, V> current = cluster.head; // May be null
        while (current != null){
            if (current.getKey().equals(key))
                return current.getValue();
            current = current.next;
        }
        return null;
    }

    /**
     * Doubles the size of the array (approximately)
     */
    private void rehash(){
        MyLinkedList<K, V>[] oldHashTable = hashTable;
        capacity  = nextPrime(2*capacity); // Theoretically it is better to pick the next prime
        hashTable = new MyLinkedList[capacity]; // New hash table
        for (int i = 0; i < capacity; i++){
            hashTable[i] = new MyLinkedList<>(); // Initialize linked lists for the new table
        }

        for (MyLinkedList<K, V> row : oldHashTable){
            MyListNode<K, V> current = row.head;
            while (current != null){
                int index = hash(current.getKey()); // Find the new index of Key
                hashTable[index].insert(current.getKey(), current.getValue()); // Directly insert to its corresponding linked list
                current = current.next;
            }
        }

    }

    /**
     * @param n Prime number
     * @return Smallest prime greater than or equal to a given prime n
     */
    private int nextPrime(int n){
        while (!isPrime(n)){
            n++;
        }
        return n;
    }

    /**
     * @param n Integer
     * @return true if the given integer n is a prime number, false otherwise
     */
    private boolean isPrime(int n){
        if (n <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(n); i++){
            if (n%i == 0)
                return false;
        }
        return true;
    }

    /**
     * Adds every Value in the hash table to an ArrayList and returns it
     * @return ArrayList of Values in the hash table
     */
    public ArrayList<V> getValues(){
        ArrayList<V> allValues = new ArrayList<>();
        for (int i = 0; i < capacity; i++){
            MyLinkedList<K, V> currentList = hashTable[i];
            MyListNode<K, V> current = currentList.head;
            while (current != null){
                allValues.add(current.getValue());
                current = current.next;
            }
        }
        return allValues;
    }

}
