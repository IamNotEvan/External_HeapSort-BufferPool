import java.io.IOException;
import java.io.RandomAccessFile;
import student.TestCase;

/**
 * This is buffer pool test class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 *
 */
public class BufferPoolTest extends TestCase  {
    
    private ByteFile randomFile;
    private BufferPool bp;
    private RandomAccessFile reader;
    
    /**
     * This is set up method
     */
    public void setUp() throws IOException
    {
        randomFile = new ByteFile("file", 10);
        randomFile.writeRandomRecords();
        reader = new RandomAccessFile("file", "rw");
        bp = new BufferPool(3, reader);
    }
    
    /**
     * This test all the possible cases for getRecordAtIndex
     * 
     * @throws IOException
     *      file not found exception
     */
    public void testGetRecord() throws IOException
    {
        byte[] block = new byte[40960];
        reader.seek(0);
        reader.read(block);
        Record[] records = Record.toRecArray(block);
        
        Short key1 = records[500].getKey();
        Short key2 = bp.getRecordAtIndex(500).getKey();
        assertEquals(bp.numOfBuffers(), 1);
        assertTrue(key1.equals(key2));
        
        Short key3 = records[7].getKey();
        Short key4 = bp.getRecordAtIndex(7).getKey();
        assertEquals(bp.numOfBuffers(), 1);
        assertTrue(key3.equals(key4));
        
        Short key5 = records[1500].getKey();
        Short key6 = bp.getRecordAtIndex(1500).getKey();
        assertEquals(bp.numOfBuffers(), 2);
        assertTrue(key5.equals(key6));

        Short key7 = records[2500].getKey();
        Short key8 = bp.getRecordAtIndex(2500).getKey();
        assertEquals(bp.numOfBuffers(), 3);
        assertTrue(key7.equals(key8));

        assertFalse(bp.find(3500));
        Short key9 = records[3500].getKey();
        Short key10 = bp.getRecordAtIndex(3500).getKey();
        assertEquals(bp.numOfBuffers(), 3);
        assertTrue(key9.equals(key10));
        assertFalse(bp.find(500));
        
        
    }
    
    /**
     * This test the set record class
     * 
     * @throws IOException
     *      when file is not found
     */
    public void testSetRecord() throws IOException
    {
        Record record = new Record(7, 7);
        
        // Change the record at index 500
        bp.setRecordAtIndex(500, record);
        Short key2 = bp.getRecordAtIndex(500).getKey();
        int keyint = key2;
        assertEquals(bp.numOfBuffers(), 1);
        assertEquals(7, keyint);
        //assertTrue(key1.equals(key2)); maybe because I didn't flush it?
        bp.setRecordAtIndex(501, record);
        Short key9 = bp.getRecordAtIndex(501).getKey();
        int keyint9 = key9;
        assertEquals(bp.numOfBuffers(), 1);
        assertEquals(7, keyint9);
        
        
        
        // Change the record at index 1500
        bp.setRecordAtIndex(1500, record);
        Short key3 = bp.getRecordAtIndex(1500).getKey();
        int keyint3 = key3;
        assertEquals(bp.numOfBuffers(), 2);
        assertEquals(7, keyint3);
        
        // Change the record at index 2500
        bp.setRecordAtIndex(2500, record);
        Short key4 = bp.getRecordAtIndex(2500).getKey();
        int keyint4 = key4;
        assertEquals(bp.numOfBuffers(), 3);
        assertEquals(7, keyint4);
        
        // Change the record at index 3500 and flush and write back
        bp.setRecordAtIndex(3500, record);
        Short key5 = bp.getRecordAtIndex(3500).getKey();
        int keyint5 = key5;
        assertEquals(bp.numOfBuffers(), 3);
        assertEquals(7, keyint5);
        assertFalse(bp.find(500));
        
        assertTrue(bp.isFull());
        
        byte[] block = new byte[40960];
        reader.seek(0);
        reader.read(block);
        Record[] records = Record.toRecArray(block);
        assertEquals(records.length, 10240);
        
        Short changedKey = records[500].getKey();
        Short trueKey = record.getKey();
        assertTrue(changedKey.equals(trueKey));
    }
    
    /**
     * This test flush all method
     * 
     * @throws IOException
     *      when file is not found
     */
    public void testFlushAll() throws IOException
    {
        Record record = new Record(7, 7);
        bp.setRecordAtIndex(500, record);
        bp.setRecordAtIndex(1500, record);
        bp.setRecordAtIndex(2500, record);
        assertEquals(bp.numOfBuffers(), 3);
        bp.flushAll();
        assertEquals(bp.numOfBuffers(), 0);
        
        byte[] block = new byte[40960];
        reader.seek(0);
        reader.read(block);
        Record[] records = Record.toRecArray(block);
        assertEquals(records.length, 10240);
        
        assertEquals(record.toString(), records[500].toString());
        assertEquals(record.toString(), records[1500].toString());
        assertEquals(record.toString(), records[2500].toString());
    }

}
