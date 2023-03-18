import java.nio.ByteBuffer;

/**
 * This is record class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 * 
 *
 */
public class Record implements Comparable<Record> {

    /**
     * Size of record in bytes
     */
    public static final int SIZE_IN_BYTES = 4;
    /**
     * Index of key in record
     */
    public static final int BYTE_INDEX_KEY = 0;
    /**
     * Index of value in record
     */
    public static final int BYTE_INDEX_VALUE = 2;
    /**
     * Possible max size of key value
     */
    public static final int KEY_MAXIMUM = 30000;
    private ByteBuffer bb;

    /**
     * Constructor for this record class
     * 
     * @param key
     *      the key value
     * @param val
     *      the record value
     */
    public Record(short key, short val) {
        bb = ByteBuffer.allocate(SIZE_IN_BYTES);
        bb.putShort(BYTE_INDEX_KEY, key);
        bb.putShort(BYTE_INDEX_VALUE, val);
    }

    /**
     * This get the record key
     * 
     * @return
     *      the record's key
     */
    public short getKey() 
    {
        return bb.getShort(BYTE_INDEX_KEY);
    } 


    /**
     * This get the record value
     * 
     * @return
     *      the record's value
     *   
     */
    public short getValue() 
    {
        return bb.getShort(BYTE_INDEX_VALUE);
    }


    /**
     * This is simple constructor
     * 
     * @param key
     *      int key
     * @param val
     *      int value
     */
    public Record(int key, int val) 
    {
        this((short)key, (short)val);
    }


    
    /**
     * This is also a constructor
     * 
     * @param bytes
     *      the byte array
     */
    public Record(byte[] bytes) 
    {
        bb = ByteBuffer.wrap(bytes);
    }


    // Makes a whole array of records that are backed by the given byte array
    // Caution: Changing the array will change records and vice versa!
    /**
     * This convert byte array to record array
     * @param binaryData
     *      the byte array 
     * @return
     *      the record aray
     */
    public static Record[] toRecArray(byte[] binaryData) 
    {
        int numRecs = binaryData.length / SIZE_IN_BYTES;
        Record[] recs = new Record[numRecs];
        for (int i = 0; i < recs.length; i++) {
            int byteOffset = i * SIZE_IN_BYTES;
            ByteBuffer bb = 
                ByteBuffer.wrap(binaryData, byteOffset, SIZE_IN_BYTES);
            recs[i] = new Record(bb.slice());
        }
        return recs;
    }

    /**
     * This is also a constructor 
     * 
     * @param bb
     *      this is bytebuffer
     */
    private Record(ByteBuffer bb) 
    {
        this.bb = bb;
    }

    /**
     * copies the contents of another record. This is a DEEP copy.
     * 
     * @param other
     *      new record
     */
    public void setTo(Record other) 
    {
        bb.putShort(BYTE_INDEX_KEY, other.getKey());
        bb.putShort(BYTE_INDEX_VALUE, other.getValue());
    }


    /**
     * This compare the records
     * 
     * @param o
     *      new record
     * 
     * @return
     *      int value
     */
    @Override
    public int compareTo(Record o) 
    {
        return Short.compare(this.getKey(), o.getKey());
    }

    /**
     * This create string value
     * 
     * @return
     *      the string value
     */
    public String toString() 
    {
        StringBuilder sb = new StringBuilder("Record: (");
        sb.append(this.getKey());
        sb.append(", ");
        sb.append(this.getValue());
        sb.append(")");
        return sb.toString();
    }

}
