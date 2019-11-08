import java.io.BufferedInputStream;

/**
 * @author YaNai
 * @date 2019/11/8 9:15
 */
public class BinaryIn {
    private BufferedInputStream in;
    private int buffer1;
    private int buffer2;
    private int n1;
    private int n2;

    public BinaryIn(BufferedInputStream in) {
        this.in = in;
        buffer1 = 0;
        n1 = 0;
        realRead();
    }

    public int read(int bitNum) {
        int data = 0;
        if (bitNum < n1) {
            data = (buffer1 << (8 - n1) & 0x00FF) >> (8 - bitNum);
            n1 = n1 - bitNum;
        } else if (bitNum == n1) {
            data = (buffer1 << (8 - n1) & 0x00FF) >> (8 - bitNum);
            buffer1 = buffer2;
            n1 = n2;
            realRead();
        } else {
            int x = bitNum - n1;
            data = ((buffer1 <<(8 - n1) & 0x00FF) >> (8 - bitNum))
                    | ((buffer2 >> (8 - bitNum + n1)) & 0x00FF);
            n2 = 8 - bitNum + n1;
            buffer1 = buffer2;
            n1 = n2;
            realRead();
        }
        return data;
    }

    private void realRead() {
        if (buffer2 != -1) {
            try {
                buffer2 = in.read();
                n2 = 8;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isEOF() {
        return buffer2 == -1;
    }
}
