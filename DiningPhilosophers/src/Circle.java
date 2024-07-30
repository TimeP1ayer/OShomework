class Circle extends Thread{
    String text; // 要显示的文字
    int x;
    int y;
    int baseTime=3000;

    // 构造函数，初始化圆形的位置
    public Circle(int x,int y) {
        this.x=x;
        this.y=y;
    }

    // 设置要显示的文字
    public void setText(String text) {
        this.text = text;
    }


}
