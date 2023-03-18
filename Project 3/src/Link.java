/**
 * This is link class that will be using in linked List
 * 
 * @author Evan Lee (evan0110)
 * @version 09.30.2022
 *
 */
public class Link {
    
    private Object object;  
    private Link link;    

    /**
     * Constructor for this class
     * 
     * @param it
     *      the object in this link
     * @param next
     *      the next link
     */
    Link(Object it, Link next) 
    {
        object = it; 
        link = next; 
    }
    /**
     * Constructor for this class
     * 
     * @param next
     *      the next link
     *  
     */
    Link(Link next) 
    { 
        object = null; 
        link = next;
    }

    /**
     * Return the object
     * 
     * @return
     *      the object
     */
    public Object element() 
    {
        return object;
    }                 
    
    /**
     * Set the object for this link and return that
     * 
     * @param it
     *      the object
     * @return
     *      the object
     */
    public Object setElement(Object it)
    {
        this.object = it;
        return object;
    } 
    
    /**
     * Return the next link
     * 
     * @return
     *      the next link
     */
    public Link next()
    {
        return link; 
    } 
    
    /**
     * Set next link and return that
     * 
     * @param next
     *      the next link
     * @return
     *      the next link
     */
    public Link setNext(Link next)
    {
        link = next;
        return link;
    }      

}
 