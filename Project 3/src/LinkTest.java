/**
 * This is test class for the linked list
 * 
 * @author Evan Lee (evan0110)
 * @version 09.30.2022
 *
 */
public class LinkTest extends student.TestCase {
    
    private Link a;
    private Link b;
    private String c;
    
    /**
     * Set up class for the test class
     */
    public void setUp()
    {
        c = "abc";
        a = new Link(c, null);
        b = new Link(null);
    }
    
    /**
     * Test element()
     */
    public void testElement()
    {
        assertNull(b.element());
        assertEquals(a.element(), c);
    }
    
    /**
     * Test set element
     */
    public void testSetElement()
    {
        assertEquals(b.setElement(c), c);
        assertEquals(b.element(), c);
    }
    
    /**
     * Test next and set next
     */
    public void testNextandSetNext()
    {
        assertNull(a.next());
        assertEquals(a.setNext(a), a);
        assertEquals(a.next(), a);
    }

}