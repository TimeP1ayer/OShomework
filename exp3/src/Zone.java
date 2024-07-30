public class Zone {

    public int size;//分区大小

    public int head;//初始地址

    public String state;//分区状态

    public int PID; //占用该分区的作业号,-1表示空闲

    //分区初始化
    public Zone(int size, int head, int PID) {
        this.size = size;
        this.head = head;
        this.state = "空闲";
        this.PID = PID;
    }
}
