import java.util.Scanner;

public class Sfile {
    String FileName;//文件名
    String[] protectCode= {"0","0","0"};//保护码 1可读 2可写 3可执行
    int FileLength;//文件长度
    String content;//文件内容

    public Sfile(){}

    public Sfile(String FileName,String[] protectCode){
        this.FileName=FileName;
        this.protectCode=protectCode;
        FileLength=0;
        content="";
    }

    public void write(StringBuilder stringBuilder){
        this.content = String.valueOf(stringBuilder);
        FileLength = content.length();
    }

    public void showMessage(){
        System.out.println("FileName:"+this.FileName);
        System.out.println("protectCode:"+this.protectCode[0]+this.protectCode[1]+this.protectCode[2]);
        System.out.println("FileLength:"+FileLength);
    }

    //重命名
    public void reName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入文件的新名称");
        String newName = scanner.nextLine();
        System.out.println("文件名称已从"+this.FileName+" 变为 "+newName);
        this.FileName=newName;
    }

    public static Sfile initCreate(){
        Sfile sfile = new Sfile();
        sfile.FileLength=0;
        sfile.content="";

        Scanner scanner = new Scanner(System.in);
        System.out.println("文件名：");
        sfile.FileName = scanner.nextLine();

        System.out.println("文件可读?\n1.是2.否");
        int i = scanner.nextInt();

        System.out.println("文件可写?\n1.是2.否");
        int j = scanner.nextInt();

        System.out.println("文件可执行?\n1.是2.否");
        int k = scanner.nextInt();

        if(i==1){
            sfile.protectCode[0]="1";
        }
        if (j==1){
            sfile.protectCode[1]="2";
        }
        if(k==1){
            sfile.protectCode[2]="3";
        }
        scanner.nextLine();


        return sfile;
    }

}


