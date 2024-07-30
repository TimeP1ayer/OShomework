import java.util.LinkedList;


public class memory {
    public int memorySize;//内存总大小
    public LinkedList<Zone> freeZones;//空闲分区表

    /**
     * 初始化内存
     *
     * @param memorySize 内存大小
     */
    public memory(int memorySize) {
        this.memorySize = memorySize;
        this.freeZones = new LinkedList<>();
        //向分区表加入空闲分区
        freeZones.add(new Zone(memorySize,0,-1));
    }

    /**
     * 首次适应算法
     *
     * @param size 需要分配的大小
     * @param PID  作业号
     */
    public void findFirstFree(int size,int PID) {
        //最佳分区的下标
        int index = 0;
        //判断是否找到
        boolean flag = false;
        //遍历寻找
        for (int i = 0; i < freeZones.size(); i++) {
            if(freeZones.get(i).state.equals("空闲") && (freeZones.get(i).size > size)){
                //找到足够大的
                index = i;
                flag = true;
                break;
            }
        }
        if (flag){
            allocation(size,index,freeZones.get(index),PID);
        }else {
            //找不到空闲分区
            System.out.println("当前内存中不可以存放"+size+"的内存分区");
        }
    }

    /**
     * 分配内存
     *
     * @param size     大小
     * @param index    下标
     * @param freeZone 空闲分区
     * @param PID 作业号
     */
    public void allocation(int size, int index, Zone freeZone,int PID){
        //创建新空闲分区
        Zone newZone = new Zone( freeZone.size - size,freeZone.head + size,-1);
        freeZones.add(index + 1,newZone);
        //将当前分区置为占用状态
        freeZone.size = size;
        freeZone.state = "占用";
        freeZone.PID = PID;
    }

    /**
     * 分区回收
     *
     * @param PID 作业号
     */
    public void recycle(int PID){
        Zone zone = null;
        int index = -1;

        //遍历查找
        for (int i = 0; i < freeZones.size(); i++) {
            if(freeZones.get(i).PID == PID){
                zone = freeZones.get(i);
                index = i;
                break;
            }
        }

        //判断是否存在该分区
        if(zone == null){
            System.out.println("请输入正确的分区号!");
            return;
        }

        //判断分区是否被分配
        if(zone.state.equals("空闲")){
            System.out.println("此分区是空闲分区，无需回收");
            return;
        }

        //判断前后分区是否是空闲的，是则进行合并
        boolean front = false;
        boolean next = false;

        if(index - 1 >= 0){
            front = freeZones.get(index - 1).state.equals("空闲");
        }

        if(index + 1 <= freeZones.size() - 1){
            next = freeZones.get(index + 1).state.equals("空闲");
        }

        if(front && next){
            //前后都是空的,直接把三个都变成一个，下标变为前一个的
            Zone frontZone = freeZones.get(index - 1);
            Zone nextZone = freeZones.get(index + 1);
            frontZone.size = frontZone.size + zone.size + nextZone.size;
            frontZone.PID = -1;
            //移除后两个
            freeZones.remove(index + 1);
            freeZones.remove(index);
        }else if(front){
            //前一个是空的，后一个不是
            Zone frontZone = freeZones.get(index - 1);
            frontZone.size = frontZone.size + zone.size;
            //移除
            freeZones.remove(index);
            zone.state = "空闲";
            zone.PID = -1;
        }else if(next){
            //后一个是空的，前一个不是
            Zone nextZone = freeZones.get(index + 1);
            zone.size = zone.size + nextZone.size;
            //移除后一个
            freeZones.remove(index + 1);
            zone.state = "空闲";
            zone.PID = -1;
        }else {
            //前后都非空，改变该分区状态
            zone.state = "空闲";
            zone.PID = -1;
        }

        System.out.println("成功回收"+zone.size+"大小的空间");
    }

    /**
     * 打印当前分区表
     */
    public void printCurrent() {
        System.out.println("分区编号\t分区始址\t分区大小\t空闲状态\t作业号");
        for (int i = 0; i < freeZones.size(); i++){
            Zone zone = freeZones.get(i);
            System.out.println(i + "\t\t" + zone.head + "\t\t" +
                    zone.size + "  \t" + zone.state+ "  \t" + (zone.PID == -1 ? "": zone.PID));
        }
        System.out.println();
    }

}


