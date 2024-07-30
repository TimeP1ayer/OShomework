import java.util.ArrayList;
import java.util.Scanner;

public class UFD{
    String username;
    ArrayList<Sfile>UFD=new ArrayList<>();
    ArrayList<AFD>AFD=new ArrayList<>();//运行文件目录
    int m=5;//每次只可以保存m个文件


    public UFD(String username){
        super();
        this.username = username;
        if(username.equals("user1")){
            Sfile s1 = new Sfile("File1", new String[]{"1", "2", "3"});
            Sfile s2 = new Sfile("File2", new String[]{"1", "2", "3"});
            Sfile s3 = new Sfile("File3", new String[]{"1", "2", "3"});
            UFD.add(s1);
            UFD.add(s2);
            UFD.add(s3);
        }


    }

    public int[] FindByName(){
        int[] state = new int[2];
        state[0]=-1;
        state[1]=-1;
        System.out.println("输入文件名:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int i = 0;
        for (Sfile sfile:UFD){
            if(name.equals(sfile.FileName)){
                state[0]=i;
            }
            i++;
        }

        int j = 0;
        for (AFD afd:AFD){
            if(name.equals(afd.FileName)){
                state[1]=j;
            }
            j++;
        }
        return state;
    }

    //创建文件
    public void create(){
        if (UFD.size()>=m){
            System.out.println("系统分配用户文件数已到达上限，不可以再创建新文件");
            return;
        }

        System.out.println("执行指令create");
        Sfile sfile = Sfile.initCreate();
        if(sfile!=null){
            UFD.add(sfile);
            System.out.println("已创建文件:"+sfile.FileName);
        }
    }

    /**删除文件
     * 存在该文件并且未打开
     */
    public void delete(){
        System.out.println("执行指令delete");
        int []state = FindByName();
        if(state[0]!=-1&&state[1]==-1){
            //检查是否可写
            if(UFD.get(state[0]).protectCode[1].equals("2")){
                System.out.println(UFD.get(state[0]).FileName+" 文件已被删除!");
                UFD.remove(state[0]);
            }else{
                System.out.println("没有写权限!");
            }
        }else if (state[0]==-1){
            System.out.println("文件不存在");
        }else if (state[1]!=-1){
            System.out.println("文件已被打开，请先关闭文件再删除");
        }
    }

    /**打开文件
     *
     */
    public void open(){
        System.out.println("执行指令open");
        int []state = FindByName();
        if(state[0]!=-1&&state[1]==-1){
            System.out.println("打开文件: "+UFD.get(state[0]).FileName);
            AFD afd = new AFD(UFD.get(state[0]).FileName,UFD.get(state[0]).protectCode);
            AFD.add(afd);
        }else if (state[0]==-1){
            System.out.println("文件不存在");
        }else if (state[1]!=-1){
            System.out.println("文件已经被打开");
        }
    }

    /**关闭文件
     *
     */
    public void close(){
        System.out.println("执行指令close");
        int []state = FindByName();
        if(state[0]!=-1&&state[1]!=-1){

            System.out.println("关闭文件: "+UFD.get(state[0]).FileName);
            AFD.remove(state[1]);

        }else if (state[0]==-1){
            System.out.println("文件不存在");
        }else if (state[1]==-1){
            System.out.println("文件未被打开");
        }
    }

    /**读出文件内容
     *
     */
    public void read(){
        System.out.println("执行指令read");
        int []state = FindByName();
        if(state[0]!=-1&&state[1]!=-1){
            //检查是否可读
            if(UFD.get(state[0]).protectCode[0].equals("1")){
                System.out.println("文件 "+UFD.get(state[0]).FileName+" 内容:");
                System.out.println(UFD.get(state[0]).content);
                System.out.println("文件读取结束");
            }else {
                System.out.println("没有读权限");
            }
        }else if (state[0]==-1){
            System.out.println("文件不存在");
        }else if (state[1]==-1){
            System.out.println("文件未被打开");
        }
    }

    /**写文件
     *
     */
    public void write(){
        System.out.println("执行指令write");
        StringBuilder stringBuilder = new StringBuilder();
        int []state = FindByName();
        if(state[0]!=-1&&state[1]!=-1){
            //检查是否可写
            if(UFD.get(state[0]).protectCode[1].equals("2")){
                Sfile sfile = UFD.get(state[0]);
                stringBuilder.append(sfile.content);

                System.out.println("输入内容:");
                Scanner scanner = new Scanner(System.in);
                String newS = scanner.nextLine();

                stringBuilder.append(newS);
                sfile.write(stringBuilder);
                //重新写入
                UFD.remove(state[0]);
                UFD.add(sfile);
            }else {
                System.out.println("没有写权限");
            }
        }else if (state[0]==-1){
            System.out.println("文件不存在");
        }else if (state[1]==-1){
            System.out.println("文件未被打开");
        }
    }

    /**显示文件信息
     *
     */
    public void showMessage(){
        System.out.println("执行指令showMessage");
        int []state = FindByName();
        if(state[0]!=-1){
            UFD.get(state[0]).showMessage();
        }else if (state[0]==-1){
            System.out.println("文件不存在");
        }
    }

    /**修改文件名字
     *
     */
    public void rename(){
        System.out.println("执行指令rename");
        int []state = FindByName();
        if(state[0]!=-1&&state[1]==-1){
            Sfile sfile = UFD.get(state[0]);
            UFD.remove(state[0]);
            sfile.reName();
            UFD.add(sfile);

        }else if (state[0]==-1){
            System.out.println("文件不存在");
        }else if (state[1]!=-1){
            System.out.println("文件已被打开，请先关闭文件再修改名称");
        }
    }

    /**
     * 展示目录
     */
    public void displayPath(){
        System.out.println("执行指令displayPath");
        System.out.println("UFD当前路径:");
        for (Sfile sfile:UFD){
            System.out.print(sfile.FileName+"  ");
        }
        System.out.println();

        System.out.println("AFD当前路径:");
        for (AFD  afd:AFD){
            System.out.print(afd.FileName+"  ");
        }
        System.out.println();
    }


}

