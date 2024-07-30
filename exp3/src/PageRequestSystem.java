import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PageRequestSystem {
    /** 指定页面尺寸 **/
    int pageSize = 512; //1024=1KB

    /** 指定内存页表的最大长度 **/
    int pageTableSize = 8;

    /** 页表 **/
    ArrayList<Integer> pageTable = new ArrayList<>();

    /** 初始化页表 **/
    public PageRequestSystem(){
        // 初始化页表 -1 表示该页为空
        for (int i = 0; i < pageTableSize; i++) {
            pageTable.add(-1);
        }
    }

    /** 请求页式存储管理
     *
     * @param addressSequence 地址串
     */
    private  void PageRequests(ArrayList<Integer> addressSequence) {
        // 记录页面失效次数
        int numPageFaults = 0;
        //存储页号，用于FIFO算法
        Queue<Integer> fifoQueue = new LinkedList<>();

        for (Integer address : addressSequence) {
            //计算页号
            int pageNumber = address / pageSize;

            //该页已经在内存中
            if (pageTable.contains(pageNumber)) {
                System.out.println("访问指令 " + address +" 所在页号:" + pageNumber + "：页表情况：" + pageTable);
            } else {
                if (pageTable.contains(-1)) {
                    //主存中仍有未使用的页，直接调换页面入主存
                    int index = pageTable.indexOf(-1);
                    pageTable.set(index, pageNumber);

                    //将该页记录到队列的末尾，用于下一次的fifo淘汰
                    fifoQueue.add(pageNumber);
                    System.out.println("访问指令 " + address + "：发生页面失效，调入页 " + pageNumber + "，页表情况：" + pageTable);
                    numPageFaults++;
                } else {
                    //页表已被完全使用，使用fifo算法淘汰页面，获取页表中最先进入的页号A
                    int evictedPage = fifoQueue.poll();
                    //获取页A在主存中的位置
                    int evictedPageIndex = pageTable.indexOf(evictedPage);
                    //指定位置替换为新页
                    pageTable.set(evictedPageIndex, pageNumber);
                    //新页进入fifo队尾进行记录
                    fifoQueue.add(pageNumber);
                    System.out.println("访问指令 " + address + "：发生页面失效，调入页 " + pageNumber + "，淘汰页 " + evictedPage + "，页表情况：" + pageTable);
                    //失效次数+1
                    numPageFaults++;
                }
            }
        }
        System.out.println("页面失效次数：" + numPageFaults);
    }

    public static void main(String[] args) {

        PageRequestSystem prs = new PageRequestSystem();

        // 生成指令地址流
        ArrayList<Integer> addressSequence = Address.generateAddressSequence(10,prs.pageSize);

        // 请求页式存储管理
        prs.PageRequests(addressSequence);
    }
}