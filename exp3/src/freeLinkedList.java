public class freeLinkedList {

    public static void main(String[] args) {

        System.out.println("=========空闲链表============");
        memory memory = new memory(640);

        //分配系统分区40KB
        memory.findFirstFree(40,0);
        memory.printCurrent();

        //作业1申请130KB
        memory.findFirstFree(130,1);
        memory.printCurrent();

        //作业2申请60KB
        memory.findFirstFree(60,2);
        memory.printCurrent();

        //作业3申请100KB
        memory.findFirstFree(100,3);
        memory.printCurrent();

        //作业2释放60KB
        memory.recycle(2);
        memory.printCurrent();

        //作业4申请200KB
        memory.findFirstFree(130,1);
        memory.printCurrent();

        //作业3释放100KB
        memory.recycle(3);
        memory.printCurrent();

        //作业1释放130KB
        memory.recycle(1);
        memory.printCurrent();

        //作业5申请140KB
        memory.findFirstFree(140,5);
        memory.printCurrent();

        //作业6申请60KB
        memory.findFirstFree(60,6);
        memory.printCurrent();

        //作业7申请50KB
        memory.findFirstFree(50,7);
        memory.printCurrent();

    }
}
