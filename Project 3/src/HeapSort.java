import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This is heap sort class
 * 
 * @author Evan Lee (evan0110)
 * @version 10.11.2022
 *
 */
public class HeapSort {

    /**
     * This is the entry point of the application
     * 
     * @param args
     *            Command line arguments
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws Exception
    {
        String fileName = args[0];
        String numBufferStr = args[1];
        String statFileName = args[2];
        
        File inputFile = new File(fileName);  
        RandomAccessFile reader = new RandomAccessFile(inputFile, "rw");
        int numRecords = (int)(reader.length() / 4);
        
        
       
        int numBuf = Integer.parseInt(numBufferStr);   
        BufferPool bufferPool = new BufferPool(numBuf, reader);
        
        long start = System.currentTimeMillis();
        MaxHeap heap = new MaxHeap(bufferPool, numRecords, numRecords);
        heap.buildHeap();
        heap.heapSort();
        bufferPool.flushAll();
        long total = System.currentTimeMillis() - start;
        
        printInDec(reader, numRecords / 1024);
        System.out.println();
        
        int cacheHits = bufferPool.getCacheHits();
        int cacheMisses = bufferPool.getCacheMisses();
        int diskReads = bufferPool.getDiskReads();
        int diskWrites = bufferPool.getDiskWrites();

        createStatFile(statFileName, cacheHits, cacheMisses,
            diskReads, diskWrites, total, fileName);
        

        reader.close();
    }
    
    /**
     * This prints our what we need
     * 
     * @param reader
     *      random access file
     * @param numBlocks
     *      number of blocks in file
     * @throws IOException
     *      when file not found
     */
    public static void printInDec(RandomAccessFile reader,
        int numBlocks) throws IOException
    {
        for (int i = 0; i < numBlocks; i++)
        {
            byte[] one = new byte[4];
            int offset = i * 4096;
            reader.seek(offset);
            reader.read(one);
            Record[] record = Record.toRecArray(one);
      
            Short key = record[0].getKey();
            Short value = record[0].getValue();
            System.out.printf("%5d %5d\t", key, value);
            if ((i != 0) && ((i + 1) % 8 == 0))
            {
                System.out.println();
            }
        }
    }
    
    /**
     * Create new stat file
     * 
     * @param statName
     *      file name
     * @param cacheHits
     *      cachehits
     * @param cacheMisses
     *      cachemisses
     * @param diskReads
     *      diskReads
     * @param diskWrites
     *      diskWrties
     * @param time
     *      total time
     * @param fileName
     *      file name
     * @throws IOException
     *      when file not found
     */
    public static void createStatFile(String statName, int cacheHits,
        int cacheMisses, int diskReads, int diskWrites,
        long time, String fileName) throws IOException
    {
        @SuppressWarnings("unused")
        File statFile = new File(statName);
        FileWriter writer = new FileWriter(statName, true);
        BufferedWriter bWriter = new BufferedWriter(writer);
        bWriter.write("------  STATS ------\n");
        bWriter.write("File name: " + fileName + "\n");
        bWriter.write("Cache Hits: " + cacheHits + "\n");
        bWriter.write("Cache Misses: " + cacheMisses + "\n");
        bWriter.write("Disk Reads: " + diskReads + "\n");
        bWriter.write("Disk Writes: " + diskWrites + "\n");
        bWriter.write("Time to sort: " + time + "\n");
        bWriter.close();
    }

}
