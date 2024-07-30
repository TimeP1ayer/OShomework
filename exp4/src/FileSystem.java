import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileSystem {

    int n;//用户上限数

    public FileSystem(){
        Map<String,UFD>MFD=new HashMap();
        Scanner scanner = new Scanner(System.in);
        UFD defaultuser = new UFD("user1");
        MFD.put("user1",defaultuser);

        while(true){
            System.out.println("1.登陆\n2.创建新用户\n3.退出");
            int c = scanner.nextInt();
            scanner.nextLine();
            switch (c){
                case 1:{
                    System.out.println("输入用户名称:");
                    String username = scanner.nextLine();
                    if (MFD.containsKey(username)){
                        System.out.println("存在此用户，进入用户界面");
                        login(MFD.get(username));
                    }else {
                        System.out.println("不存在此用户");
                    }
                    break;
                }

                case 2:{

                    if(MFD.size()>=n){
                        System.out.println("用户数量已到达系统上限，不可以再创建新用户");
                        break;
                    }

                    System.out.println("输入用户名称:");
                    String username = scanner.nextLine();
                    UFD ufd = new UFD(username);
                    MFD.put(username,ufd);
                }

                case 3:{
                    System.exit(1);
                    break;
                }

                default:{
                    System.out.println("输入有误，请重新输入");
                }
            }
        }
    }

    public void login(UFD ufd){
        System.out.println("登陆用户:"+ufd.username);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("帮助:\n" +
                    "1.create\n" +
                    "2.delete\n" +
                    "3.open\n" +
                    "4.close\n" +
                    "5.read\n" +
                    "6.write\n" +
                    "7.display\n" +
                    "8.message\n" +
                    "9.rename\n" +
                    "exit");
            String code = scanner.nextLine();
            switch (code){

                case "create":{
                    ufd.create();
                    break;
                }

                case "delete":{
                    ufd.delete();
                    break;
                }

                case "open":{
                    ufd.open();
                    break;
                }
                case "close":{
                    ufd.close();
                    break;
                }
                case "read":{
                    ufd.read();
                    break;
                }
                case "write":{
                    ufd.write();
                    break;
                }
                case "display":{
                    ufd.displayPath();
                    break;
                }
                case "message":{
                    ufd.showMessage();
                    break;
                }
                case "rename":{
                    ufd.rename();
                    break;
                }
                case "exit":{
                    ufd.displayPath();
                    System.exit(0);
                }
                default:{
                    System.out.println("指令错误请重新输入");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
    }
}
