import java.io.IOException;
import java.io.RandomAccessFile;
/**
 * This is a Buffer class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 * 
 *
 */
public class Buffer {
    
    private byte[] buffer;
    private boolean changed;
    private RandomAccessFile reader;
    private Record[] records;
    private int blockIndex;
    
    
    /**
     * This is buffer constructor
     * 
     * @param recordIndex
     *      record index that was passed
     * @param reader1
     *      random access file
     * @throws IOException
     *      when file is not found
     */
    public Buffer(int recordIndex, RandomAccessFile reader1) throws IOException
    {
        reader = reader1;
        changed = false;
        buffer = new byte[4096]; 
        blockIndex = (recordIndex * 4) / 4096 * 4096;
        reader.seek(blockIndex);
        reader.read(buffer);
        setRecords(Record.toRecArray(buffer));
    }
    
    /**
     * This method mark if this buffer is changed
     */
    public void changed()
    {
        this.changed = true;
    }
    
    /**
     * Return the record array
     * 
     * @return
     *      record array
     */
    public Record[] getRecords() 
    {
        return records;
    }

    /**
     * Set the record array
     * 
     * @param records
     *      new record array
     */
    public void setRecords(Record[] records) 
    {
        this.records = records;
    }
    
    /**
     * This writes backs to the file if the buffer has changed
     * 
     * @throws IOException
     *      when file is not found
     */
    public void writeBack() throws IOException
    {
        if (changed)
        {
            reader.seek(blockIndex);
            reader.write(buffer);
            this.changed = false;
        }
    }
    
    /**
     * Find out if that record is in this buffer
     * 
     * @param recordIndex
     *      looking record index
     * @return
     *      true/false
     */
    public boolean find(int recordIndex)
    {
        int byteOffset = recordIndex * 4;
        
        return (blockIndex <= byteOffset && byteOffset < blockIndex + 4096);
 
    }
    
    /**
     * Get that record at index
     * 
     * @param n
     *      index
     * @return
     *      that record
     */
    public Record getRecord(int n)
    {
        if (this.find(n))
        {
            return this.records[n % 1024];
        }
        return null;
    }
    
    /**
     * Set the record at this index
     * 
     * @param n
     *      index that need to be updated
     * @param record
     *      new record
     */
    public void setRecord(int n, Record record)
    {
        int index = n % 1024;
        this.records[index].setTo(record);
    
    }    
}
