import java.util.ArrayList;

/**
 * This MaxHeap class is only used for building heap, so there are some methods omitted
 */
public class MaxHeap<T extends Comparable<T>>{

    private T[] array; // Internal array where we store Posts

    private int size;

    MaxHeap(){
        array = (T[]) new Comparable[10];
        size = 0;
    }

    /**
     * Accepts an initial array of Posts and builds a heap from it.
     * @param initialArray Array of posts given to the constructor
     * Time Complexity - O(N)
     */
    MaxHeap(T[] initialArray){
        array = (T[])new Comparable[initialArray.length+1];
        System.arraycopy(initialArray, 0, array, 1, initialArray.length); // Leave 0'th index empty because of the implementation of insert
        size = initialArray.length;
        buildHeap();
    }

    /**
     * Deletes the value at index 1(biggest) and percolates down hole 1 to secure heap property
     * @return the deleted node
     */
    public T deleteMax(){
        if (size == 0)
            return null;
        T maxItem = array[1];
        array[1] = array[size--];
        percolateDown(1);
        return maxItem;
    }

    /**
     * Given an index, finds its true position by comparing it with indexes below it and locates it there
     * @param hole Index of the value we want to percolate down
     */
    public void percolateDown(int hole){
        int childIndex;
        T initialValue = array[hole]; // The value we want to find true position of

        while (hole*2 <= size){ // Continue if index hole has at least 1 child
            childIndex = hole*2; // Left child of index hole
            if (childIndex != size && array[childIndex+1].compareTo(array[childIndex]) > 0) // If this child is not the last index and hole's right child(we now know it exists) is greater than left child
                childIndex++; // Switch to Right child
            // Here, childIndex is the index of the hole's biggest valued child
            if (array[childIndex].compareTo(initialValue) > 0) // Child is greater than its parent ---> violation of max heap property
                array[hole] = array[childIndex]; // Move bigger child up
            else // Otherwise, we found the position for initialValue
                break;
            hole = childIndex; // New hole is childIndex since we moved it upwards, now this index is logically empty
        }
        array[hole] = initialValue; // Hole is the true position for initialValue, set it.
        // Note: If initialValue is already in its true position, loop will break in first loop and nothing will change as it can be seen.
    }

    /**
     * Starting from the last non-leaf index, percolate down until index 1 (inclusive)
     */
    private void buildHeap(){
        int startingIndex = size/2;
        for (int i = startingIndex; i >= 1; i--){
            percolateDown(i);
        }
    }



    public int getSize(){
        return size;
    }
}
