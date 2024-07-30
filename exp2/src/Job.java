import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Job {
    String username;//用户名
    String jobname;//作业名称
    LocalTime arrivalTime;//到达时间
    LocalTime startTime;//开始运行时间
    LocalTime runTime;//运行所需时间
    LocalTime finishTime;//完成时间
    int memory;//需要的内存
    int tape;//需要的磁带机
    char state;//状态  f完成，r执行中，r就绪

    public Job(String username, String jobname, LocalTime arrivalTime, LocalTime runTime, int memory, int tape) {
        this.username = username;
        this.jobname = jobname;
        this.arrivalTime = arrivalTime;
        this.runTime = runTime;
        this.memory = memory;
        this.tape = tape;
        this.state = 'r';
    }

    //输出作业开始时间，完成时间，周转时间，带权周转时间，以及本组任务的平均周转时间和带权平均周转时间
    public static void showMessage(ArrayList<Job> finishJob){
        System.out.println("作业     到达时间     开始时间     完成时间     周转时间   带权周转时间");

        //总周转时间
        double totalTurnaroundTime = 0;
        //总带权周转时间之和
        double totalWeightedTurnaroundTime = 0;

        for (Job job : finishJob) {
            //周转时间
            long turnaroundTime = ChronoUnit.MINUTES.between(job.arrivalTime,job.finishTime);
            //实际运行时间
            long ActualRunTime = ChronoUnit.MINUTES.between(job.startTime,job.finishTime);
            //带权周转时间
            long weightedTurnaroundTime =  turnaroundTime / ActualRunTime;

            totalTurnaroundTime += turnaroundTime;
            totalWeightedTurnaroundTime += weightedTurnaroundTime;

            System.out.print("" + job.jobname);
            System.out.print("    " + job.arrivalTime);
            System.out.print("       " + job.startTime);
            System.out.print("       " + job.finishTime);
            System.out.print("       " + turnaroundTime);
            System.out.print("        " + weightedTurnaroundTime);
            System.out.println();
        }
        System.out.println("本组任务的平均周转时间："+totalTurnaroundTime/ finishJob.size());
        System.out.println("本组任务的带权平均周转时间："+totalWeightedTurnaroundTime/finishJob.size());
    }

    public LocalTime getRunTime() {
        return runTime;
    }

 }
