import java.io.IOException;

/**
 * This is Max heap class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.11.2022
 *
 * @param <T>
 *      the key
 */
class MaxHeap<T extends Comparable<T>> {

    private ArrayLike bp;
    private int capacity; // Number of records in file
    private int n; // Number of things currently in heap

    /**
     * Constructor supporting preloading of heap contents
     * 
     * @param bp2
     *      buffer pool
     * @param heapSize
     *      heap size
     * @param capacity
     *      capacity
     * @throws IOException 
     *      when file not found
     */
    MaxHeap(ArrayLike bp2, int heapSize, int capacity) throws IOException 
    {
        bp = bp2;
        n = heapSize;
        this.setCapacity(capacity);
    }

// Get and Set----------------------------------------------------------------
    /**
     * Return the number of records in file
     * 
     * @return
     *      capacity
     */
    public int getCapacity() 
    {
        return capacity;
    }

    /**
     * Set the max capacity
     * 
     * @param capacity
     *      max capacity
     */
    public void setCapacity(int capacity) 
    {
        this.capacity = capacity;
    }
    
    /**
     * Forcefully changes the heap size. May require build-heap afterwards
     * 
     * @param newSize
     *      new size of heap
     */
    public void setHeapSize(int newSize) {
        n = newSize;
    }

    /**
     * Return current size of the heap
     * 
     * @return
     *      heapSize
     */
    public int getHeapSize() {
        return n;
    }
// Get and Set----------------------------------------------------------------

    /**
     * Return position for left child of pos
     * 
     * @param pos
     *      position number
     * @return
     *      left child's pos
     */
    public static int leftChild(int pos)
    {
        return 2 * pos + 1;
    }

    /**
     * Return position for the parent of pos
     * 
     * @param pos
     *      position number
     * @return
     *      parent's position
     */
    public static int parent(int pos) 
    {
        return (pos - 1) / 2;
    }
    
    /**
     * Return true if pos a leaf position, false otherwise
     * 
     * @param pos
     *      index number
     * @return
     *      if it is leaf or not
     */
    public boolean isLeaf(int pos) {
        return (n / 2 <= pos) && (pos < n);
    }
    
    /**
     * Organize contents of array to satisfy the heap structure
     * @throws IOException 
     */
    public void buildHeap() throws IOException {
        for (int i = parent(n - 1); i >= 0; i--)
        {
            siftDown(i);
        }
    }

    /**
     * Moves an element down to its correct place
     * 
     * @param pos
     *      index number
     * @throws IOException 
     */
    public void siftDown(int pos) throws IOException 
    {
        while (!isLeaf(pos)) 
        {
            int child = leftChild(pos);
            if ((child + 1 < n) && isGreaterThan(child + 1, child)) {
                child = child + 1; // child is now index with the smaller value
            }
            if (!isGreaterThan(child, pos)) {
                return; // stop early
            }
            swap(pos, child);
            pos = child; // keep sifting down
        }
    }
    /**
     * Remove and return maximum value
     * 
     * @return
     *      the removed value
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
    public T removeMax() throws IOException {
        
        n--;
        if (n > 0) {
            swap(0, n); // Swap maximum with last value
            siftDown(0); // Put new heap root val in correct place
        }
        return (T)bp.getRecordAtIndex(n);
    }
    
    /**
     * swaps the elements at two positions
     * 
     * @param pos1
     *      first index
     * @param pos2
     *      second index
     * @throws IOException 
     */
    private void swap(int pos1, int pos2) throws IOException 
    {
        Record record1 = new Record(bp.getRecordAtIndex(pos1).getKey(),
            bp.getRecordAtIndex(pos1).getValue());
        Record record2 = new Record(bp.getRecordAtIndex(pos2).getKey(),
            bp.getRecordAtIndex(pos2).getValue());
       
        bp.setRecordAtIndex(pos1, record2);
        bp.setRecordAtIndex(pos2, record1);
    }

    /**
     * does fundamental comparison used for checking heap validity
     * 
     * @param pos1
     *      first index
     * @param pos2
     *      second index
     * @return
     *      is first index key greater than second one or not
     * @throws IOException 
     */
    private boolean isGreaterThan(int pos1, int pos2) throws IOException 
    {
        Short firstKey = bp.getRecordAtIndex(pos1).getKey();
        Short secondKey = bp.getRecordAtIndex(pos2).getKey();
        return firstKey.compareTo(secondKey) > 0;
    }
    
    /**
     * This is heap sort
     * 
     * @throws IOException
     *      when file not found
     */
    public void heapSort() throws IOException
    {
        for (int i = 0; i < capacity; i++ )
        {
            this.removeMax();
        }
    }
}