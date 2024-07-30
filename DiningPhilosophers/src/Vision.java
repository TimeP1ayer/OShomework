import javax.swing.*;
import java.awt.*;

public class Vision {
    public static void main (String[]args){
        //初始化
        int baseX=300,baseY=200;

        Philosopher philosopher1 = new Philosopher(baseX-0, baseY-150);
        Philosopher philosopher2 = new Philosopher(baseX+150, baseY-50);
        Philosopher philosopher3 = new Philosopher(baseX+110, baseY+110);
        Philosopher philosopher4 = new Philosopher(baseX-110, baseY+110);
        Philosopher philosopher5 = new Philosopher(baseX-150, baseY-50);

        philosopher1.setId(1);
        philosopher2.setId(2);
        philosopher3.setId(3);
        philosopher4.setId(4);
        philosopher5.setId(5);
        Philosopher[] philosophers = new Philosopher[]{philosopher1, philosopher2, philosopher3, philosopher4, philosopher5};

        chop chop1 = new chop((philosopher1.x+philosopher2.x)/2+50, (philosopher1.y+philosopher2.y)/2,1);
        chop chop2 = new chop((philosopher2.x+philosopher3.x)/2+40, (philosopher2.y+philosopher3.y)/2+30,2);
        chop chop3 = new chop((philosopher3.x+philosopher4.x)/2+25, (philosopher3.y+philosopher4.y)/2+50,3);
        chop chop4 = new chop((philosopher4.x+philosopher5.x)/2, (philosopher4.y+philosopher5.y)/2+30,4);
        chop chop5 = new chop((philosopher5.x+philosopher1.x)/2, (philosopher5.y+philosopher1.y)/2,5);
        chop[] chops = new chop[]{chop1, chop2, chop3, chop4, chop5};

        //分配筷子
        philosopher1.setFork(chop1, chop5);
        philosopher2.setFork(chop2, chop1);
        philosopher3.setFork(chop3, chop2);
        philosopher4.setFork(chop4, chop3);
        philosopher5.setFork(chop5, chop4);

        //启动线程
        for (chop chop : chops) {
            Thread thread = new Thread(chop);
            thread.start();
        }
        for (Philosopher philosopher : philosophers) {
            Thread thread = new Thread(philosopher);
            thread.start();
        }

        //创建窗口
        JFrame frame = new JFrame("哲学家进餐问题");
        frame.setSize(750,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel downPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //绘制图形
                for(chop fork: chops){
                    fork.draw(g);
                }
                for(Philosopher philosopher:philosophers){
                    philosopher.draw(g);
                }
                repaint();
            }
        };

        frame.setResizable(false);
        frame.add(downPanel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
