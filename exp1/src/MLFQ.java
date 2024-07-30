import java.util.*;

//抢占式多级反馈队列调度算法
public class MLFQ {

    PCB[] pcbs;
    int currentQueue=0;
    int currentTime=0;
    int leftTS=0;

    LevelQueue[] queues;
    //等待到达队列
    LevelQueue waitQueue = new LevelQueue(0,0);

    //已到达队列
    LevelQueue readyQueue1 = new LevelQueue(1,3);
    LevelQueue readyQueue2 = new LevelQueue(2,2);
    LevelQueue readyQueue3 = new LevelQueue(300,1);

    public MLFQ(PCB[] pp){
        pcbs = pp;
        queues = new LevelQueue[3];
        for (PCB p:pcbs){
            waitQueue.add(p);
        }
        queues[0]=readyQueue1;
        queues[1]=readyQueue2;
        queues[2]=readyQueue3;
    }

    public void checkArrival(){
        Iterator<PCB> iterator = waitQueue.iterator();
        while(iterator.hasNext()){
            PCB p=iterator.next();
            if(p.arrivalTime ==currentTime){
                iterator.remove();
                readyQueue1.add(p);
                System.out.println("进程："+ p.name +"  到达");
                currentQueue=0;
            }
        }
    }

    public void run(){
        while (true){
            System.out.println("当前时间："+currentTime);
            //全部队列为空
            if(waitQueue.size()==0&&queues[0].size()==0&&queues[1].size()==0&&queues[2].size()==0){
                System.out.println("所有任务已完成");
                break;
            }
            checkArrival();
            //从第一个队列处理
            while(currentQueue!=3){
                if (queues[currentQueue].size()==0){
                    System.out.println("队列 "+currentQueue+" 完成,跳转到下一队列进行任务");
                    System.out.println();
                    currentQueue++;
                }else {
                    singlePCB();
                    break;
                }
            }
            System.out.println("------------------------------------------------");
            currentQueue=0;
            currentTime++;
        }
    }

    //输入PCB
    public void PCBs(){
        System.out.println("MLFQ:");
        System.out.println("进程个数:");
        Scanner scanner = new Scanner(System.in);
        int i=scanner.nextInt();
        pcbs=new PCB[i];
        for (int j = 0; j < i; j++) {
            PCB p=new PCB();
            p.initMLFQ();
            pcbs[j]=p;
        }
    }

    //处理单个PCB
    public void singlePCB(){
        if (queues[currentQueue].size()==0){
            System.out.println("队列"+currentQueue+"为空");
            return;
        }
        //取得当前队列第一个进程
        PCB pcb= (PCB) queues[currentQueue].get(0);
        pcb.runTime--;
        pcb.state='r';

        //上一个时间片用完，切换进程,分配时间片
        if(leftTS==0){
            leftTS=queues[currentQueue].timeSlice;
            System.out.println("正在运行 " + pcb.name);
            leftTS--;

            //首次分配时间片后的完成情况
            if(pcb.runTime ==0){
                queues[currentQueue].remove(pcb);
                System.out.println(pcb.name+" 完成");
                leftTS=0;
                pcb.state='f';
            }else {
                //未完成转移到下一个队列
                System.out.println(pcb.name+" 未完成");
                queues[currentQueue].remove(pcb);
                if(currentQueue==2){
                    //已经是最后一个队列
                    queues[currentQueue].add(pcb);
                }else {
                    queues[currentQueue+1].add(pcb);
                }

            }
        }else {
            //时间片未用完
            leftTS--;
            if(pcb.runTime <=0){
                //作业完成了，移除
                leftTS=0;
                queues[currentQueue].remove(pcb);
                System.out.println(pcb.name+" 完成");
                pcb.state='f';
            }else {
                //作业未完成
                System.out.println(pcb.name+" 未完成");
                pcb.state='w';
                if(currentQueue==2){
                    //已经是最后一个队列,添加到队尾
                    queues[currentQueue].remove(pcb);
                    queues[currentQueue].add(pcb);
                }else {
                    //进入下一队列
                    queues[currentQueue+1].add(pcb);
                }
            }
        }
        System.out.println("本次时间片处理任务 "+ pcb.name+" 队列位置："+currentQueue +" 分配时间片："+queues[currentQueue].timeSlice+" 剩余时间片:"+leftTS);

        disPCB();
        System.out.println();
    }

    void disPCB(){

        ArrayList<PCB> p1 = queues[0];
        ArrayList<PCB> p2 = queues[1];
        ArrayList<PCB> p3 = queues[2];
        System.out.println();

        System.out.println("当前队列");
        System.out.println("队列0:");
        for (PCB p:p1){
            System.out.println(p.toStringR());
        }

        System.out.println("队列1:");
        for (PCB p:p2){
            System.out.println(p.toStringR());
        }

        System.out.println("队列2:");
        for (PCB p:p3){
            System.out.println(p.toStringR());
        }
    }

    public static void main(String[] args) {
        PCB p1 = new PCB("进程1",2,3);
        PCB p2 = new PCB("进程2",0,2);
        PCB p3 = new PCB("进程3",3,1);
        PCB p4 = new PCB("进程4",4,10);
        PCB p5 = new PCB("进程5",1,5);

        PCB[] p = new PCB[]{p1,p2,p3,p4,p5};
        MLFQ mlfq = new MLFQ(p);
        mlfq.run();
    }



}
