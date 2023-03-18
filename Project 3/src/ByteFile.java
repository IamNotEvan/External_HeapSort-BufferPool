import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Random;
import student.TestableRandom;

/**
 * Basic handling of binary data files.
 * Uses a single byte array as a buffer for disc operations
 * Assumes that Records are composed of a short key, and
 * a short value.
 * 
 * @author Evan Lee (evan0110)
 * @version 10.27.2022
 */
public class ByteFile {

    private File theFile;
    private int numBlocks;
    private long seed;
    private static int recordsPerBlock = 1024;
    private static int bytesPerBlock = recordsPerBlock
        * Record.SIZE_IN_BYTES; // 4096

    /**
     * This is constructor
     * 
     * @param filename
     *      the file name
     * @param numBlocks
     *      number of blocks
     */
    public ByteFile(String filename, int numBlocks) 
    {
        theFile = new File(filename);
        this.numBlocks = numBlocks;
        this.seed = -1;
    }

    /**
     * checks if a file of records is sorted or not
     * 
     * @return
     *      file is sorted or not
     * @throws IOException
     *      when file is not found
     */
    public boolean isSorted() throws IOException {
        byte[] basicBuffer = new byte[bytesPerBlock];

        RandomAccessFile raf = new RandomAccessFile(theFile, "r");
        try {
            short currKey = Short.MIN_VALUE;

            for (int block = 0; block < numBlocks; block++) {
                raf.read(basicBuffer);                
                Record[] recsInBlock = Record.toRecArray(basicBuffer);
                for (int rec = 0; rec < recordsPerBlock; rec++) {
                    short nextKey = recsInBlock[rec].getKey();
                    if (currKey > nextKey) {
                        raf.close();
                        return false;
                    }
                    else {
                        currKey = nextKey;
                    }
                }
            }
        }
        finally {
            raf.close();
        }
        return true;
    }
    
    /**
     * creates a file of randomly generated records
     * 
     * @throws IOException
     *      when file is not found
     */
    public void writeRandomRecords() throws IOException {
        Random rng = new TestableRandom();
        if (seed != -1) {
            rng.setSeed(seed);
        }

        byte[] basicBuffer = new byte[bytesPerBlock];
        ByteBuffer bb = ByteBuffer.wrap(basicBuffer);
        
        theFile.delete();
        RandomAccessFile raf = new RandomAccessFile(theFile, "rw");
        try {
            for (int block = 0; block < numBlocks; block++) {
                for (int rec = 0; rec < recordsPerBlock; rec++) {
                    short key = (short)rng.nextInt(Record.KEY_MAXIMUM);
                    short val = (short)rng.nextInt(Short.MAX_VALUE);
                    // puts the data in the basicBuffer...
                    bb.putShort(key);
                    bb.putShort(val);
                }
                raf.write(bb.array());
                // ^^^ the slow, costly operation!!! Good thing we use buffer
                bb.clear(); // resets the position of the buffer in array
            }
        }
        finally {
            raf.close();
        }
    }

}
