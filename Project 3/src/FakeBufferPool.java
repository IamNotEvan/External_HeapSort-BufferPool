/**
 * This is fake buffer pool class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 *
 */
public class FakeBufferPool implements ArrayLike {
    
    private Record[] records;
    
    /**
     * This is constructor for this class
     * 
     * @param length
     *      lengthe of the record array
     */
    public FakeBufferPool(int length)
    {
        records = new Record[length];
        
    }
    
    /**
     * Get record at that index
     * @param pos
     *      position
     * @return
     *      the record
     */
    @Override
    public Record getRecordAtIndex(int pos) {
        
        return records[pos];
    }

    /**
     * Set record at that index
     * 
     * @param pos
     *      position
     * @param rec
     *      new record
     */
    @Override
    public void setRecordAtIndex(int pos, Record rec) {
       
        this.records[pos] = (rec);
    }
    
    /**
     * This prints out the array
     * 
     * @return
     *      the string value of array
     */
    public String toString()
    {
        StringBuilder buildStr = new StringBuilder("");
        for (int i = 0; i < records.length; i++)
        {
            buildStr.append(records[i].toString());
            buildStr.append("\n");
        }
        return buildStr.toString();
    }

}
