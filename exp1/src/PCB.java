import java.util.Scanner;

public class PCB {
    String name;//进程名字
    char state='w';//状态 w r f,默认等待
    int priority;//优先级
    int arrivalTime;//到达时间
    int runTime;//运行时间
    int endTime;//结束时间

    public PCB(){

    }

    public PCB(String name, int arrivalTime, int runTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.runTime = runTime;
    }

    public PCB(String name, int runTime) {
        this.name = name;
        this.runTime = runTime;
    }

    void initA(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("进程名称:");
        this.name=scanner.next();

        System.out.println("进程优先级:");
        this.priority=scanner.nextInt();

        System.out.println("进程运行时间:");
        this.runTime =scanner.nextInt();

        this.state='w';
    }

    void initRoundRobin(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("进程名称:");
        this.name=scanner.next();

        System.out.println("进程到达时间:");
        this.arrivalTime =scanner.nextInt();

        System.out.println("进程需要的运行时间:");
        this.runTime =scanner.nextInt();

    }

    void initMLFQ(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("进程名称:");
        this.name=scanner.next();

        System.out.println("进程需要的运行时间:");
        this.runTime =scanner.nextInt();

    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public String toStringR() {
        return "PCB{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", ntime=" + arrivalTime +
                ", rtime=" + runTime +
                '}';
    }
}


