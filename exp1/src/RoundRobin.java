import java.util.*;

//简单轮转法
public class RoundRobin {
    //等待到达队列
    ArrayList<PCB>waitQueue=new ArrayList();
    //到达队列
    ArrayList<PCB>readyQueue=new ArrayList();
    int timeSlice=2;
    int currentTime =0;
    int leftTS = 0;

    public RoundRobin(PCB[] pcbs){
        for (PCB p:pcbs){
                waitQueue.add(p);
        }
        Collections.sort(waitQueue, Comparator.comparingInt(PCB::getArrivalTime));
    }

    //检查是否有进程到达
    public void checkArrival(){
        Iterator<PCB> iterator = waitQueue.iterator();
        while (iterator.hasNext()){
            PCB p = iterator.next();
            if (p.arrivalTime ==currentTime){
                iterator.remove();
                readyQueue.add(p);
                System.out.println("进程："+ p.name +"  到达");
            }
        }
    }

    //对单个进程的单次时间片处理
    void runSingle(){

        if (readyQueue.isEmpty()){
            return;
        }

        PCB pcb = readyQueue.get(0);
        pcb.runTime--;


    //上一个时间片已用完,切换进程
        if (leftTS==0) {
            //分配时间片
            leftTS=timeSlice;
            System.out.println("正在运行 " + pcb.name);
            pcb.state = 'r';

            leftTS--;
            if (pcb.runTime == 0) {
                //完成
                System.out.println(pcb.name + " 完成");
                leftTS=0;
                pcb.state = 'f';
                readyQueue.remove(0);
            }

        }else {
            //上一个时间片未用完
            leftTS--;
            leftTS=0;

            if (pcb.runTime == 0) {
                //本次时间片完成
                System.out.println(pcb.name + " 完成");
                pcb.state = 'f';
                readyQueue.remove(0);
            }else {
                //本次时间片未完成
                System.out.println("执行完毕时刻:"+(currentTime+1)+"  "+pcb.name + " 未完成 仍需要时间：" +pcb.runTime);
                readyQueue.remove(0);
                readyQueue.add(pcb);
            }
        }
        disPCB();
    }

    //展示就绪队列信息
    void disPCB(){
        System.out.println("当前时刻：");
        if(readyQueue.size()==0){
            System.out.println("无任务");
        }
        for (PCB p:readyQueue){
            System.out.println(p.toStringR());
        }
    }

    void run(){

        while (waitQueue.size()!=0||readyQueue.size()!=0){
            System.out.println("时间："+currentTime);
            checkArrival();
            runSingle();
            System.out.println();
            currentTime++;
        }
        System.out.println("finish all at "+currentTime);
    }

    public static void main(String[] args) {

        PCB p1=new PCB("进程1",0,5);
        PCB p2=new PCB("进程2",1,2);
        PCB p3=new PCB("进程3",2,7);
        PCB p4=new PCB("进程4",3,7);
        PCB p5=new PCB("进程5",28,7);

        PCB[] pcbs=new PCB[5];
        pcbs[0]=p1;
        pcbs[1]=p2;
        pcbs[2]=p3;
        pcbs[3]=p4;
        pcbs[4]=p5;

        RoundRobin roundRobin = new RoundRobin(pcbs);
        roundRobin.run();
    }

}
