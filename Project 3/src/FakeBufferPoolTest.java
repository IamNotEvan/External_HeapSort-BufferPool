import student.TestCase;
/**
 * This is fake buffer pool test
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 *
 */
public class FakeBufferPoolTest extends TestCase {
    
    private FakeBufferPool fake;
    private Record a;
    private Record b;
    private Record c;
    private Record d;
    private Record e;
   
    /**
     * This is set up method for this test class
     */
    public void setUp()
    {
        fake = new FakeBufferPool(5);
        a = new Record(1, 1);
        b = new Record(2, 1);
        c = new Record(3, 1);
        d = new Record(4, 1);
        e = new Record(5, 1);   
    }
    
    /**
     * This test all the methods
     */
    public void testAll()
    {
        fake.setRecordAtIndex(0, a);
        fake.setRecordAtIndex(1, b);
        fake.setRecordAtIndex(2, c);
        fake.setRecordAtIndex(3, d);
        fake.setRecordAtIndex(4, e);
        
        assertEquals(fake.getRecordAtIndex(0), a);
        assertEquals(fake.getRecordAtIndex(1), b);
        assertEquals(fake.getRecordAtIndex(2), c);
        assertEquals(fake.getRecordAtIndex(3), d);
        assertEquals(fake.getRecordAtIndex(4), e);
        
        assertEquals(fake.toString(), "Record: (1, 1)\n"
            + "Record: (2, 1)\n"
            + "Record: (3, 1)\n"
            + "Record: (4, 1)\n"
            + "Record: (5, 1)\n");
    }

}
