import java.util.ArrayList;
import java.util.Random;

public class Address {

    /**
     * 生成指令地址流
     * @param numInstructions 指令条数
     * @param pageSize  页面大小
     * @return 返回地址流
     */
    public static ArrayList<Integer> generateAddressSequence(int numInstructions, int pageSize) {
        ArrayList<Integer> addressSequence = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numInstructions; i++) {
            // 50% 的指令是顺序执行的
            if (i < numInstructions * 0.5) {
                addressSequence.add(i * pageSize);
            } else if (i < numInstructions * 0.75) {
                // 使生成地址均匀散布在前地址部分
                addressSequence.add(rand.nextInt(numInstructions / 2) * pageSize);
            } else {
                // 使生成地址均匀散布在后地址部分
                addressSequence.add(rand.nextInt(numInstructions / 2) * pageSize + numInstructions / 2 * pageSize);
            }
        }
        return addressSequence;
    }
}
