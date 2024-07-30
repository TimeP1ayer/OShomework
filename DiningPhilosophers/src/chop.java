import java.awt.*;

public class chop extends Circle{

    int id;//筷子id
    int diameter=50;//直径
    Color color=Color.YELLOW;//默认颜色
    boolean occupy=false;//占用状态
    int occupyId;//占用id

    public chop(int x, int y, int id) {
        super(x,y);
        this.id=id;
    }

    //设置文本
    @Override
    public void setText(String text) {
        this.text=text;
    }

    //更新筷子状态
    public void update(){
        if (occupy){
            this.color=Color.YELLOW;
            this.setText("被"+ occupyId +"占用");
        }else{
            this.color=Color.GREEN;
            this.setText("空闲");
        }
    }

    //占用筷子
    public void Occupy(int ocid){
        this.occupyId =ocid;
        this.occupy=true;
    }

    //释放筷子
    public void release(){
        this.occupyId =-1;
        this.occupy=false;
    }

    //绘制筷子
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 15));
        int textWidth = g.getFontMetrics().stringWidth("筷子 "+id);
        g.drawString("筷子 "+id, x + diameter / 2 - textWidth / 2, y + diameter / 2+textWidth/5);
        int statusWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x + diameter / 2 - statusWidth / 2, y + diameter / 2-statusWidth/5);
    }

    @Override
    public void run() {
        while (true){
            update();
        }
    }
}
