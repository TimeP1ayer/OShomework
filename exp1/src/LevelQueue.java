import java.util.ArrayList;

public class LevelQueue extends ArrayList {
    int timeSlice;//时间片,1,2,3
    int level;//队列等级

    public LevelQueue(int timeSlice, int level) {
        super();
        this.timeSlice = timeSlice;
        this.level = level;
    }

}
