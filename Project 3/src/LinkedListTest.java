import student.TestCase;
/**
 * Test class for linked list 
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 *
 */
public class LinkedListTest extends TestCase {
    
    private LinkedList link;
    private String p;
    private String b;
    private String c;
    
    /**
     * This is setUp method
     */
    public void setUp()
    {
        p = "a";
        b = "b";
        c = "c";
        link = new LinkedList();
    }
    
    /**
     * This method test append
     */
    public void testAppend()
    {
        assertTrue(link.append(p));
        assertTrue(link.append(p));
        assertEquals(link.length(), 2);
        link.clear();
        assertEquals(link.length(), 0);
    }
    
    /**
     * This method test remove
     */
    public void testRemove()
    {
        assertTrue(link.append(p));
        assertTrue(link.append(p));
        assertTrue(link.append(p));
        
        link.moveToStart();
        assertEquals(link.remove(), p);
        assertEquals(link.length(), 2);
        
        link.moveToStart();
        assertEquals(link.remove(), p);
        assertEquals(link.length(), 1);
        
        link.moveToStart();
        assertEquals(link.remove(), p);
        assertEquals(link.length(), 0);
        
        link.moveToStart();
        assertNull(link.remove());
    }
    
    /**
     * This method test get curr method
     */
    public void testGetCurr()
    {
        assertTrue(link.append(p));
        assertTrue(link.append(b));
        assertTrue(link.append(c));
        
        link.moveToStart();
        link.next();
        
        assertEquals(link.getCurr().element(), b);
    }
    
    /**
     * This method test add At front method
     */
    public void testAddAtFront()
    {
        String d = "d";
        String e = "e";
        String f = "f";
        String g = "g";
        
        assertTrue(link.append(p));
        assertTrue(link.append(b));
        assertTrue(link.append(c));
        assertTrue(link.append(d));
        assertTrue(link.append(e));
        assertTrue(link.append(f));
        assertTrue(link.append(g));
        assertEquals(link.length(), 7);
        
        assertTrue(link.addAtFront(b));
        assertEquals(link.length(), 8);
        
        link.moveToStart();
        assertEquals(link.getCurr().element(), b);    
    }
    
    /**
     * This method test add at front again
     */
    public void testAddAtFront2()
    {
        assertTrue(link.addAtFront(b));
        assertEquals(link.length(), 1);
        link.moveToStart();
        assertEquals(link.getCurr().element(), b);  
        
    }
    
    /**
     * This method test move to front method
     */
    public void testMoveToFront()
    {
        String d = "d";
        String e = "e";
        String f = "f";
        String g = "g";
        
        assertTrue(link.append(p));
        assertTrue(link.append(b));
        assertTrue(link.append(c));
        assertTrue(link.append(d));
        assertTrue(link.append(e));
        assertTrue(link.append(f));
        assertTrue(link.append(g));
        assertEquals(link.length(), 7);
        
        
        link.moveToStart();
        link.next();
        link.next();
        assertEquals(link.getCurr().element(), c);
        
        link.moveToFront(link.getCurr());
        link.moveToStart();
        assertEquals(link.getCurr().element(), c);
        assertEquals(link.length(), 7);
        
        
        link.moveToStart();
        link.next();
        link.next();
        link.next();
        assertEquals(link.getCurr().element(), d);
        
    }
    
    /**
     * This method test move to last method
     */
    public void testMoveToLast()
    {
        assertTrue(link.append(p));
        assertTrue(link.append(b));
        assertTrue(link.append(c));
        assertEquals(link.length(), 3);

        link.moveToLast();
        assertEquals(link.getCurr().element(), c);
    }
    
    /**
     * This method test remove last method
     */
    public void testRemoveLast()
    {
        assertTrue(link.append(p));
        assertTrue(link.append(b));
        assertTrue(link.append(c));
        assertEquals(link.length(), 3); 
        assertEquals(link.removeLast(), c);
        assertEquals(link.length(), 2);
    }

}
