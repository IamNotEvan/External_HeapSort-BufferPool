import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This is buffer pool class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.17.2022
 *
 */
public class BufferPool implements ArrayLike {
    
    private LinkedList bufferList;
    private  int maxSize;
    private RandomAccessFile reader;  
    private int cacheHits;
    private int cacheMisses;
    private int diskReads;
    private int diskWrites;
    
   
    /**
     * Constructor for this class
     * 
     * @param numBuffer
     *      max size of this buffer pool
     * @param reader1
     *      the random access file
     */
    public BufferPool(int numBuffer, RandomAccessFile reader1) 
    {
        bufferList = new LinkedList();
        maxSize = numBuffer;
        reader = reader1;
        setCacheHits(0);
        setCacheMisses(0);
        setDiskReads(0);
        setDiskWrites(0);
    }
    
    /**
     * Get the cache hits
     * 
     * @return
     *      cache hits number
     */
    public int getCacheHits() {
        return cacheHits;
    }

    /**
     * Set the cache hits number
     * 
     * @param cacheHits
     *      new cache hits number
     */
    public void setCacheHits(int cacheHits) {
        this.cacheHits = cacheHits;
    }

    /**
     * Get the cache misses number
     * 
     * @return
     *      cache misses number
     */
    public int getCacheMisses() {
        return cacheMisses;
    }

    /**
     * Set the cache misses number
     * 
     * @param cacheMisses
     *      new cache misses number
     */
    public void setCacheMisses(int cacheMisses) {
        this.cacheMisses = cacheMisses;
    }

    /**
     * Get the disk reads number
     * 
     * @return
     *      the disk read number
     */
    public int getDiskReads() {
        return diskReads;
    }

    /**
     * Set the disk read number
     * 
     * @param diskReads
     *      new disk read number
     */
    public void setDiskReads(int diskReads) {
        this.diskReads = diskReads;
    }

    /**
     * Get the disk write number
     * 
     * @return
     *      disk write number
     */
    public int getDiskWrites() {
        return diskWrites;
    }

    /**
     * Set the disk write number
     * 
     * @param diskWrites
     *      new disk write number
     */
    public void setDiskWrites(int diskWrites) {
        this.diskWrites = diskWrites;
    }
    
    /**
     * Return the current size of buffer pool
     * 
     * @return
     *      the size of buffer pool
     */
    public int numOfBuffers()
    {
        return this.bufferList.length();
    }
    
    /**
     * Add the buffer to the front of buffer pool
     * 
     * @param newBuffer
     *      new newBuffer
     */
    public void addToFront(Buffer newBuffer)
    {
        bufferList.addAtFront(newBuffer);
    }
    
    /**
     * Create a new buffer that has given index number it its block
     * 
     * @param index
     *      index number of records
     * @return
     *      the new created buffer
     * @throws IOException 
     *      when file is not found
     */
    public Buffer createBuffer(int index) throws IOException
    {
        Buffer newBuffer = new Buffer(index, reader);
        return newBuffer;
    }
    
    /**
     * Remove the last one in linked list and wrtie back if it was changed
     * @throws IOException 
     *      when file is not found
     */
    public void flushLast() throws IOException
    {
        diskWrites++;
        Buffer removedBuffer = (Buffer)bufferList.removeLast();
        removedBuffer.writeBack();
    }
    
    /**
     * Flush all the buffer in the buffer pool
     * 
     * @throws IOException
     *      when file is not found
     */
    public void flushAll() throws IOException
    {  
        while (this.numOfBuffers() >= 1)
        {
            diskWrites++;
            bufferList.moveToStart();
            Buffer removedBuffer = (Buffer)bufferList.remove();
            removedBuffer.writeBack();
            bufferList.next();
        }
    }
    
    /**
     * Check if buffer pool is full or not
     * 
     * @return
     *      if buffer is full or not
     */
    public boolean isFull()
    {
        return (bufferList.length() == maxSize);
    }
    
    /**
     * Find if that index of record is in buffer pool 
     * 
     * @param index
     *      index or record
     * @return
     *      if that record is in or not
     */
    public boolean find(int index)
    {
        bufferList.moveToStart();
        for (int i = 0; i < bufferList.length(); i++)
        {
            Buffer buffer = (Buffer)bufferList.getValue();
            if (buffer.find(index))
            {
                return true;
            }
            bufferList.next();
        }
        return false;
    }
    
    /**
     * Get record at that index
     * @param index
     *      position
     * @return
     *      the record
     * @throws IOException
     *      when file is not found
     */
    @Override
    public Record getRecordAtIndex(int index) throws IOException
    {
        Record result;
        if (this.find(index))
        {
            cacheHits++;
            Buffer buffer = (Buffer)bufferList.getCurr().element();
            result = buffer.getRecord(index);
            bufferList.moveToFront(bufferList.getCurr());
            return result;
        }
        else
        {
            diskReads++;
            cacheMisses++;
            if (this.isFull())
            {
                this.flushLast();
                Buffer newBuffer = this.createBuffer(index);
                this.addToFront(newBuffer);             
                result = newBuffer.getRecord(index);
                return result;
            }
            else
            {               
                Buffer newBuffer = this.createBuffer(index);
                this.addToFront(newBuffer);            
                result = newBuffer.getRecord(index);
                return result;
            }
        }  
    }
    
    /**
     * Set record at that index
     * 
     * @param index
     *      position
     * @param newRecord
     *      new record
     * @throws IOException
     *      when file is not found
     */
    @Override
    public void setRecordAtIndex(int index, Record newRecord) throws IOException
    {
        if (this.find(index))
        {
            Buffer buffer = (Buffer)bufferList.getCurr().element();
            buffer.setRecord(index, newRecord);
            bufferList.moveToFront(bufferList.getCurr());
            buffer.changed();
        }
        else
        {
            if (this.isFull())
            {
                this.flushLast();
                Buffer newBuffer = this.createBuffer(index);
                this.addToFront(newBuffer);
                newBuffer.setRecord(index, newRecord);
                newBuffer.changed();
            }
            else
            {
                Buffer newBuffer = this.createBuffer(index);
                this.addToFront(newBuffer);               
                newBuffer.setRecord(index, newRecord);                
                newBuffer.changed();
            }
        }       
    } 
}
