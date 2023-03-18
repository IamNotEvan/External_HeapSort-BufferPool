import java.io.IOException;

/**
 * This is Array Like interface
 * @author Evan LEe (evan0110)
 * @version 10.27.2022
 *
 */
public interface ArrayLike {

    /**
     * Get record at that index
     * @param pos
     *      position
     * @return
     *      the record
     * @throws IOException
     *      when file is not found
     */
    public Record getRecordAtIndex(int pos) throws IOException;
    
    /**
     * Set record at that index
     * 
     * @param pos
     *      position
     * @param rec
     *      new record
     * @throws IOException
     *      when file is not found
     */
    public void setRecordAtIndex(int pos, Record rec) throws IOException;
    
}