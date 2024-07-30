import java.awt.*;
import java.util.Random;

class Philosopher extends Circle {
    int id;//哲学家id
    chop leftChop;//左筷子
    chop rightChop;//右筷子
    boolean right;//检测右手
    boolean left;//检测左手
    String text;//描述文字
    int diameter=100;//直径
    Color color=Color.red;//默认颜色

    public Philosopher(int x,int y){
        super(x,y);
    }

    //思考
    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id +" 在思考");
        colorFix();
        Thread.sleep(baseTime);
    }

    //左手拿起筷子
    private void takeLeft() throws InterruptedException {
        System.out.println("Philosopher " + id + " " + "拿起了左边的筷子");
        leftChop.Occupy(id);
        left=true;
        colorFix();
        Thread.sleep(baseTime);
    }

    //左手放下筷子
    private void releaseLeft() throws InterruptedException {
        System.out.println("Philosopher " + id + " " + "放下了左边的筷子");
        leftChop.release();
        left=false;
        colorFix();
        Thread.sleep(baseTime);
    }

    //右手拿起筷子
    private void takeRight() throws InterruptedException {
        System.out.println("Philosopher " + id + " " + "拿起了右边的筷子");
        rightChop.Occupy(id);
        right=true;
        colorFix();
        Thread.sleep(baseTime);
    }

    //右手放下筷子
    private void releaseRight() throws InterruptedException {
        System.out.println("Philosopher " + id + " " + "放下了右边的筷子");
        rightChop.release();
        right=false;
        colorFix();
        Thread.sleep(baseTime);
    }

    //吃饭，然后放下所有筷子
    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " " + "正在吃饭，然后准备放下筷子");
        colorFix();
        Thread.sleep(baseTime*2);
    }

    //颜色修正
    public void colorFix(){
        if(right^left){
            //只有一只手有筷子的状态
            text="手上有筷子";
            color=Color.YELLOW;
        }
        if (right&&left){
            //进餐状态
            text="在吃饭";
            color=Color.GREEN;
        }
        if (!right&&!left){
            //思考状态
            text="正在思考";
            color=Color.RED;
        }
    }

    //分配筷子
    public void setFork(chop leftChop, chop rightChop){
        this.leftChop = leftChop;
        this.rightChop = rightChop;
    }

    //设置id
    public void setId(int id) {
        this.id = id;
    }

    //绘制哲学家
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 20));
        int textWidth = g.getFontMetrics().stringWidth(text);
        int idWidth = g.getFontMetrics().stringWidth("哲学家 "+id);
        g.drawString(text, x + diameter / 2 - textWidth / 2, y + diameter / 2);
        g.drawString("哲学家 "+id, x + diameter / 2 - idWidth / 2, y + diameter / 2+idWidth/3);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 思考
                try {

                    //哲学家动作二选一
                    Random random = new Random();
                    if (random.nextInt(2)==0){
                        think();
                    }else {
                        System.out.println("Philosopher " + id + " " + "想要吃饭了");
                        // 拿起左边的筷子
                        if (!leftChop.occupy) {
                            takeLeft();
                        }
                        // 拿起右边的筷子
                        if (!rightChop.occupy) {
                            takeRight();
                        }
                        // 进餐
                        if (right && left) {
                            eat();
                        }
                        // 放下左边的筷子
                        if (left){
                            releaseLeft();
                        }

                        // 放下右边的筷子
                        if (right){
                            releaseRight();
                        }


                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

