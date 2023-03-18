import java.io.IOException;
import java.io.RandomAccessFile;
import student.TestCase;

/**
 * This is MaxHeap test class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 *
 */
public class MaxHeapTest extends TestCase {
    
    private MaxHeap<?> heap;
    @SuppressWarnings("rawtypes")
    private MaxHeap realHeap;
    private BufferPool bp;
    private FakeBufferPool fbp;
    private RandomAccessFile reader;
    private ByteFile randomFile;
    private int a;
    
    /**
     * This is set up method for this test class
     */
    @SuppressWarnings("rawtypes")
    public void setUp() throws IOException
    {
        randomFile = new ByteFile("file", 1);
        randomFile.writeRandomRecords();
        reader = new RandomAccessFile("file", "rw");
        
        a = (int)(reader.length() / 4);
        bp = new BufferPool(1, reader);
        
        realHeap = new MaxHeap(bp, a, a);
        
        fbp = new FakeBufferPool(10);
        heap = new MaxHeap(fbp, 10, 10);
    }

    /**
     * Test with fake buffer pool
     * 
     * @throws IOException
     *      when file not found
     */
    public void testFake() throws IOException
    {
        Record record1 = new Record(1, 1);
        Record record2 = new Record(2, 1);
        Record record3 = new Record(3, 1);
        Record record4 = new Record(4, 1);
        Record record5 = new Record(5, 1);
        Record record6 = new Record(6, 1);
        Record record7 = new Record(7, 1);
        Record record8 = new Record(8, 1);
        Record record9 = new Record(9, 1);
        Record record10 = new Record(10, 1);
        
        fbp.setRecordAtIndex(0, record2);
        fbp.setRecordAtIndex(1, record3);
        fbp.setRecordAtIndex(2, record4);
        fbp.setRecordAtIndex(3, record1);
        fbp.setRecordAtIndex(4, record5);
        fbp.setRecordAtIndex(5, record7);
        fbp.setRecordAtIndex(6, record8);
        fbp.setRecordAtIndex(7, record6);
        fbp.setRecordAtIndex(8, record10);
        fbp.setRecordAtIndex(9, record9);
        
        //System.out.println(fbp.toString());

        heap.buildHeap();
        //System.out.println(fbp.toString());
        
        heap.heapSort();
        //System.out.println(fbp.toString());
        
    }
    
    /**
     * This is for the real test
     * 
     * @throws IOException
     *      when file not found
     */
    @SuppressWarnings("rawtypes")
    public void testReal() throws IOException
    {
        realHeap.buildHeap();
        realHeap.heapSort();
        bp.flushAll();
        assertTrue(randomFile.isSorted());
        
        ByteFile file3 = new ByteFile("file3", 3);
        file3.writeRandomRecords();
        RandomAccessFile reader3 = new RandomAccessFile("file3", "rw");
        BufferPool pool3 = new BufferPool(4, reader3);
        int size3 = (int)(reader3.length() / 4);
        MaxHeap heap3 = new MaxHeap(pool3, size3, size3);
        assertFalse(file3.isSorted());
        heap3.buildHeap();
        heap3.heapSort();
        pool3.flushAll();
        assertTrue(file3.isSorted());
        
        
        ByteFile file31 = new ByteFile("file31", 3);
        file31.writeRandomRecords();
        RandomAccessFile reader31 = new RandomAccessFile("file31", "rw");
        BufferPool pool31 = new BufferPool(1, reader31);
        int size31 = (int)(reader31.length() / 4);
        MaxHeap heap31 = new MaxHeap(pool31, size31, size31);
        assertFalse(file31.isSorted());
        heap31.buildHeap();
        heap31.heapSort();
        pool31.flushAll();
        assertTrue(file31.isSorted());
        
        ByteFile file32 = new ByteFile("file32", 3);
        file32.writeRandomRecords();
        RandomAccessFile reader32 = new RandomAccessFile("file32", "rw");
        BufferPool pool32 = new BufferPool(2, reader32);
        int size32 = (int)(reader31.length() / 4);
        MaxHeap heap32 = new MaxHeap(pool32, size32, size32);
        assertFalse(file32.isSorted());
        heap32.buildHeap();
        heap32.heapSort();
        pool32.flushAll();
        assertTrue(file32.isSorted());
        
        
        
        
        ByteFile file10 = new ByteFile("file10", 10);
        file10.writeRandomRecords();
        RandomAccessFile reader10 = new RandomAccessFile("file10", "rw");
        BufferPool pool10 = new BufferPool(20, reader10);
        int size10 = (int)(reader10.length() / 4);
        MaxHeap heap10 = new MaxHeap(pool10, size10, size10);
        assertFalse(file10.isSorted());
        heap10.buildHeap();
        heap10.heapSort();
        pool10.flushAll();
        assertTrue(file10.isSorted());
        
        
        
        
        
       
    }
}
