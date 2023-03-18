/**
 * This is Linked List class
 * 
 * @author Evan Lee (evan0110)
 * @version 09.30.2022
 *
 */
public class LinkedList {
    
    private Link head;        
    private Link tail;         
    private Link curr;       
    private int listSize;     

    /**
     * This is constructor for this class
     */
    public LinkedList()
    { 
        clear();
    }

    /**
     * Clear the entire linked List
     */
    public void clear() 
    {
        curr = new Link(null);
        tail = new Link(null);
        head = new Link(tail);
        listSize = 0;
    }
    
    /**
     * This method return the link at current pointer
     * 
     * @return
     *      the link
     */
    public Link getCurr()
    {
        return this.curr;
    }

    /**
     * Append it to the list
     * 
     * @param it
     *      object
     * @return
     *      always true
     */
    public boolean append(Object it) 
    {
        tail.setNext(new Link(null));
        tail.setElement(it);
        tail = tail.next();
        listSize++;
        return true;
    }
    
    /**
     * This method add object to the start of linked list
     * 
     * @param it
     *      new adding object
     * @return
     *      always true
     */
    public boolean addAtFront(Object it)
    {
        this.moveToStart();
        curr.setNext(new Link(curr.element(), curr.next()));
        curr.setElement(it);
        if (tail == curr)
        {
            tail = curr.next();
        }
            
        listSize++;
        return true;   
    }

    /**
     * Move this link to the front of linked list
     * 
     * @param link
     *      the current link
     */
    public void moveToFront(Link link)
    {
        Object removed = this.remove();
        this.addAtFront(removed);
    }
    
    /**
     * Remove the end link and return the object in that link
     * 
     * @return
     *      object in the removed object
     */
    public Object removeLast()
    {
        this.moveToLast();
        return this.remove();
    }
    
    /**
     * Move pointer to the end of linked list
     */
    public void moveToLast()
    {
        this.moveToStart();
        for (int i = 0; i < this.listSize - 1; i++)
        {
            this.next();
        }
    }

    /**
     * This method remove the current node
     * 
     * @return
     *      The removed object
     */
    public Object remove() 
    {
        if (curr == tail)
        {
            return null;
        }
        Object it = curr.element();
        curr.setElement(curr.next().element());
        if (curr.next() == tail)
        {
            tail = curr;
        }
        curr.setNext(curr.next().next());
        listSize--;
        return it;
    }
     
    /**
     * Move pointer to the first object
     */
    public void moveToStart()
    { 
        curr = head.next();
    } 
    
    /**
     * Move pointer to the next link
     */
    public void next() 
    { 
        if (curr != tail)
        {
            curr = curr.next();      
        }
    }

    /**
     * Return the number of objects in the linked list
     * 
     * @return
     *      the size
     */
    public int length() 
    { 
        return listSize; 
    }
    /**
     * Get the current node's object
     * 
     * @return
     *      object
     */
    public Object getValue() 
    {
        return curr.element(); 
    }
}

