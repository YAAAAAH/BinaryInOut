import java.io.BufferedOutputStream;

/**
 * @author YaNai
 * @date 2019/11/8 9:17
 */
public class BinaryOut {
    private BufferedOutputStream out;
    private int buffer;
    private int n;

    public BinaryOut(BufferedOutputStream out) {
        this.out = out;
        this.buffer = 0;
        this.n = 0;
    }

    public void write(int data, int bitNum) {
        if ((n + bitNum) < 8) {
            buffer <<= bitNum;
            buffer = buffer | (data << (8 - bitNum) & 0x00FF) >> (8 - bitNum);
            n += bitNum;
        } else if ((n + bitNum) == 8){
            buffer <<= bitNum;
            buffer = buffer | (data << (8 - bitNum) & 0x00FF) >> (8 - bitNum);
            realWrite();
        } else {
            int x = bitNum + n - 8;
            buffer <<= (bitNum - x);
            buffer = buffer | (data << (8 - bitNum) & 0x00FF) >> n;
            realWrite();
            buffer = data;
            n = x;
        }
    }

    private void realWrite() {
        try {
            out.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buffer = 0;
        n = 0;
    }

    public void close() {
        if (n != 0) {
            buffer = buffer << (8 - n);
            realWrite();
        }
    }
}
