import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class cpu {
    int memory=100;//现有内存
    int tape=5;//磁带机
    int tracks=3;//道数
    ArrayList<Job>NotArrivedJob=new ArrayList<>();//未到达
    ArrayList<Job>ArrivedJob=new ArrayList<>();//已到达
    ArrayList<Job> MemoryJob =new ArrayList<>();//运行中
    ArrayList<Job> finishJob=new ArrayList<>();//已完成
    LocalTime currentTime = LocalTime.of(8,55);//当前时间

    public cpu(Job[] jobs){
        for (Job job:jobs){
            NotArrivedJob.add(job);
        }
    }

    /**先来先服务
     *
     */
    public void FCFS(){
        while (true){
            //检查是否有程序到达
            if (NotArrivedJob.size()!=0){
                for (int i = 0; i < NotArrivedJob.size(); i++) {
                    if (NotArrivedJob.get(0)==null){
                        break;
                    }
                    Job nowjob = NotArrivedJob.get(0);
                    NotArrivedJob.remove(0);
                    if (nowjob.arrivalTime.equals(currentTime)){
                        //已到达
                            ArrivedJob.add(nowjob);
                            System.out.println("任务："+nowjob.username+" "+nowjob.jobname+"  到达时间： "+currentTime);
                    }else {
                        NotArrivedJob.add(nowjob);
                    }
                }
            }

            //检查是否有已到达程序可以工作
            if (ArrivedJob.size()!=0){
                //检查是否可以进入主存运行，并分配资源
                if (MemoryJob.size()<tracks){
                    for (int i = 0; i < ArrivedJob.size(); i++) {
                        boolean enter = checkFCFS(ArrivedJob.get(i));
                        if(enter){
                            //可以进入
                            Job nowjob =ArrivedJob.get(i);
                            nowjob.startTime=currentTime;
                            System.out.println("任务："+nowjob.username+" "+nowjob.jobname+" 进入运行队列时间: "+currentTime.toString());
                            occupy(nowjob);
                            ArrivedJob.remove(i);
                            MemoryJob.add(nowjob);
                            i--;
                            //display();
                        }else {
                            //不可进入
                        }

                    }
                }
            }

            //已完成全部任务
            if (ArrivedJob.size()==0&&MemoryJob.size()==0&&NotArrivedJob.size()==0){
                System.out.println("已完成全部任务");
                //TODO
                // 输出任务完成信息
                Job.showMessage(finishJob);
//                for (Job job:finishJob){
//                    System.out.println(job.username+" finish:"+job.finishTime);
//                }
                break;
            }

            //时间+5分钟
            currentTime=currentTime.plusMinutes(5);
            //执行工作
            if (MemoryJob.size()!=0){
                //每个任务的运行时间减少
                for (int i = 0; i < MemoryJob.size(); i++) {
                    if (MemoryJob.size()==0||MemoryJob.get(0)==null){
                        break;
                    }else {
                        Job nowjob = MemoryJob.get(0);
                        nowjob.runTime=nowjob.runTime.minusMinutes(5);
                        MemoryJob.remove(0);
                        if (nowjob.runTime.equals(LocalTime.of(00,00))){
                            //已完成作业，移除，回收资源
                            nowjob.finishTime=currentTime;
                            recovery(nowjob);
                            //TODO
                            System.out.println("任务："+nowjob.username+" "+nowjob.jobname+" 完成时间： "+nowjob.finishTime);
                            System.out.println();
                            i--;
                        }else{
                            //未完成，返回队列
                            MemoryJob.add(nowjob);
                        }
                    }
                }
            }
        }
    }

    /**短作业优先
     *
     */
    public void SJF(){
        while (true){
            //检查是否有程序到达
            if (NotArrivedJob.size()!=0){
                for (int i = 0; i < NotArrivedJob.size(); i++) {
                    if (NotArrivedJob.get(0)==null){
                        break;
                    }
                    Job nowjob = NotArrivedJob.get(0);
                    NotArrivedJob.remove(0);
                    if (nowjob.arrivalTime.equals(currentTime)){
                        //已到达
                        ArrivedJob.add(nowjob);
                        System.out.println("任务："+nowjob.username+" "+nowjob.jobname+"  到达时间： "+currentTime);
                    }else {
                        NotArrivedJob.add(nowjob);
                    }
                }
            }

            //检查是否有已到达程序可以工作,使用最短时间优先
            if (ArrivedJob.size()!=0){
                //检查是否可以进入主存运行，并分配资源
                if (MemoryJob.size()<tracks){
                    for (int i = 0; i < ArrivedJob.size(); i++) {
                        Job nowJob = checkSJF();
                        //没有满足条件的
                        if (nowJob==null){
                            break;
                        } else {
                            //有满足条件的,移出添加到主存
                            System.out.println("任务："+nowJob.username+" "+nowJob.jobname+" 进入运行队列时间: "+currentTime.toString());
                            nowJob.startTime = currentTime;
                            occupy(nowJob);
                            ArrivedJob.remove(nowJob);
                            MemoryJob.add(nowJob);
                            i--;
                        }
                    }
                }
            }
            //已完成全部任务
            if (ArrivedJob.size()==0&&MemoryJob.size()==0&&NotArrivedJob.size()==0){
                System.out.println("已完成全部任务");
                Job.showMessage(finishJob);
                break;
            }

            //时间+5分钟
            currentTime=currentTime.plusMinutes(5);
            //执行工作
            if (MemoryJob.size()!=0){
                //每个任务的运行时间减少
                for (int i = 0; i < MemoryJob.size(); i++) {
                    if (MemoryJob.size()==0||MemoryJob.get(0)==null){
                        break;
                    }else {
                        Job nowjob = MemoryJob.get(0);
                        nowjob.runTime=nowjob.runTime.minusMinutes(5);
                        //System.out.println("At "+currentTime+": "+nowjob.jobname+" need runTime "+nowjob.runTime);
                        MemoryJob.remove(0);
                        if (nowjob.runTime.equals(LocalTime.of(00,00))){
                            //已完成，移除，回收资源
                            nowjob.finishTime=currentTime;
                            recovery(nowjob);
                            //TODO
                            System.out.println("任务："+nowjob.username+" "+nowjob.jobname+" 完成时间： "+currentTime.toString());
                            System.out.println();
                            i--;
                        }else{
                            //未完成，返回队列
                            MemoryJob.add(nowjob);
                        }
                    }
                }
            }
        }
    }

    //展示内存信息
    public void display(){
        System.out.println("---------------------------------------");
        System.out.println("NotArrivedJob:"+NotArrivedJob.size());
        for (Job job:NotArrivedJob){
            System.out.print(job.jobname+"  ");
        }
        System.out.println();

        System.out.println("ArrivedJob:"+ArrivedJob.size());
        for (Job job:ArrivedJob){
            System.out.print(job.jobname+"  ");
        }
        System.out.println();

        System.out.println("MemoryJob:"+MemoryJob.size());
        for (Job job:MemoryJob){
            System.out.print(job.jobname+"  ");
        }
        System.out.println();
        System.out.println("---------------------------------------");

    }

    public boolean checkFCFS(Job job){
        //内存和磁盘机可分配，道数未满
        if (job.memory<this.memory&&job.tape<this.tape&&MemoryJob.size()<tracks){
            return true;
        }else {
            return false;
        }
    }

    public Job checkSJF(){
        //先找出满足内存和磁带机的工作，再找时间最短的
        ArrayList<Job>ready = new ArrayList<>();
        for (Job job:ArrivedJob){
            if (job.memory<this.memory&&job.tape<this.tape){
                ready.add(job);
            }
        }
        Collections.sort(ready, Comparator.comparing(Job::getRunTime));
        if (ready.size()!=0){
            return ready.get(0);
        }else {
            return null;
        }

    }

    //回收资源
    public void recovery(Job job){
        this.tape+= job.tape;
        this.memory+=job.memory;
        finishJob.add(job);
    }

    //占用资源
    public void occupy(Job job){
        this.tape-= job.tape;
        this.memory-=job.memory;
    }

    public static void main(String[] args) {
        Job job1=new Job("A","JOB1",LocalTime.of(9,00),LocalTime.of(0,25),20,2);
        Job job2=new Job("B","JOB2",LocalTime.of(9,20),LocalTime.of(0,30),60,1);
        Job job3=new Job("C","JOB3",LocalTime.of(9,30),LocalTime.of(0,15),45,3);
        Job job4=new Job("D","JOB4",LocalTime.of(9,35),LocalTime.of(0,20),10,2);
        Job job5=new Job("E","JOB5",LocalTime.of(9,45),LocalTime.of(0,10),25,3);


        Scanner scanner = new Scanner(System.in);
        Job[] jobs = new Job[]{job1,job2,job3,job4,job5};

            System.out.println("1.FCFS\n2.SJF\n");
            int i = scanner.nextInt();

            System.out.println(i);
            if (i==1){
                cpu cpu1 = new cpu(jobs);
                cpu1.FCFS();
            }else if (i==2){
                cpu cpu2 = new cpu(jobs);
                cpu2.SJF();
            }

    }
}
