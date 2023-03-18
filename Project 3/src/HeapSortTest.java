import java.io.RandomAccessFile;
import student.TestCase;

/**
 * This is heap sort test
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 *
 */
public class HeapSortTest extends TestCase {
    
    

    /**
     * An artificial test to get initial coverage for the
     * main method. Delete or modify this test.
     * @throws Exception 
     *      when file is not found
     */
    public void testMain() throws Exception 
    {
        
        ByteFile bFile = new ByteFile("bFile", 1);
        bFile.writeRandomRecords();
        String[] bSFile = new String[] {"bFile", "2", "samplestats.txt"};
        HeapSort.main(bSFile);
        assertTrue(bFile.isSorted());
        
        ByteFile bFilea = new ByteFile("bFilea", 3);
        bFilea.writeRandomRecords();
        String[] bSFilea = new String[] {"bFilea", "3", "samplestats.txt"};
        HeapSort.main(bSFilea);
        assertTrue(bFilea.isSorted());
        
        ByteFile bFile2 = new ByteFile("bFile2", 5);
        bFile2.writeRandomRecords();
        String[] bSFile2 = new String[] {"bFile2", "2", "samplestats.txt"};
        HeapSort.main(bSFile2);
        assertTrue(bFile2.isSorted());
        
        ByteFile bFile2a = new ByteFile("bFile2a", 5);
        bFile2a.writeRandomRecords();
        String[] bSFile2a = new String[] {"bFile2a", "7", "samplestats.txt"};
        HeapSort.main(bSFile2a);
        assertTrue(bFile2a.isSorted());
        
        ByteFile bFile3 = new ByteFile("bFile3", 7);
        bFile3.writeRandomRecords();
        String[] bSFile3 = new String[] {"bFile3", "10", "samplestats.txt"};
        HeapSort.main(bSFile3);
        assertTrue(bFile3.isSorted());
        
        ByteFile bFile4 = new ByteFile("bFile4", 10);
        bFile4.writeRandomRecords();
        String[] bSFile4 = new String[] {"bFile4", "7", "samplestats.txt"};
        HeapSort.main(bSFile4);
        assertTrue(bFile4.isSorted());
        
        ByteFile bFile4a = new ByteFile("bFile4a", 10);
        bFile4a.writeRandomRecords();
        String[] bSFile4a = new String[] {"bFile4a", "1", "samplestats.txt"};
        HeapSort.main(bSFile4a);
        assertTrue(bFile4a.isSorted());
        
        ByteFile bFile5 = new ByteFile("bFile5", 15);
        bFile5.writeRandomRecords();
        String[] bSFile5 = new String[] {"bFile5", "10", "samplestats.txt"};
        HeapSort.main(bSFile5);
        assertTrue(bFile5.isSorted());
        
        ByteFile bFile6 = new ByteFile("bFile6", 20);
        bFile6.writeRandomRecords();
        String[] bSFile6 = new String[] {"bFile6", "20", "samplestats.txt"};
        HeapSort.main(bSFile6);
        assertTrue(bFile6.isSorted());
        
        ByteFile bFile7 = new ByteFile("bFile7", 50);
        bFile6.writeRandomRecords();
        String[] bSFile7 = new String[] {"bFile7", "20", "samplestats.txt"};
        HeapSort.main(bSFile7);
        assertTrue(bFile7.isSorted());
        
        RandomAccessFile reader = new RandomAccessFile("bFile7", "rw");
        HeapSort.printInDec(reader, 4);
        
        HeapSort.createStatFile("samplestats.txt", 1, 1, 1, 1, 1, "hello");
        
        
        
        
        
           
    }
    
    /**
     * This test the exception with main
     * 
     * @throws Exception
     *      when file is not found
     */
    public void testMainException() throws Exception
    {
        String[] file = new String[] {"nofile.bin", "4", "samplestats.txt"};
        HeapSort.main(file);
        
    }

    

}
