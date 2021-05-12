package PageScheduling;

import java.util.Scanner;
//7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1
public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        while(flag){
            Disk disk = new Disk();
            System.out.print("请输入物理块的块数:");
            disk.n = scan.nextInt();
            switch (menu()){
                case 1:
                    init(disk);
                    FIFO(disk);
                    print(disk);
                    break;
                case 2:
                    init(disk);
                    LRU(disk);
                    print(disk);
                    break;
                default:
                    flag = false;
                    break;
            }
        }

    }

    public static int menu(){
        System.out.println("****************请求分页式存储管理****************");
        System.out.println("          *        1.FIFO分配           *          ");
        System.out.println("          *        2.LRU(LFU)分配       *          ");
        System.out.println("          *        0.退出               *          ");
        System.out.print("请输入选项:");
        return new Scanner(System.in).nextInt();
    }

    public static void init(Disk disk){
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入作业名:");
        disk.name = scan.next();
        System.out.print("请输入作业页面的长度:");
        disk.len = scan.nextInt();
        System.out.println("请输入作业页面的顺序:");
        disk.work = new int[disk.len];
        for (int i = 0; i < disk.len; i++) {
            disk.work[i] = scan.nextInt();
        }
        disk.page = new int[disk.n][disk.len];
        disk.symbol = new char[disk.len];
        disk.out = new int[disk.len];
        for (int i = 0; i < disk.n; i++) {
            for (int j = 0; j < disk.len; j++) {
                disk.page[i][j] = -1;
            }
        }
        for (int i = 0; i < disk.len; i++) {
            disk.out[i] = -1;
        }
    }

    public static void FIFO(Disk disk){
        boolean flag;
        for (int i = 0; i < disk.len; i++) {
            flag = true;
            if(i > 0){
                for (int j = 0; j < disk.n; j++) {
                    disk.page[j][i] = disk.page[j][i-1];
                }
            }
            for (int j = 0; j < disk.n; j++) {
                if(disk.work[i] == disk.page[j][i]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                disk.symbol[i] = '+';
                disk.out[i] = disk.page[disk.n-1][i];
                for (int j = disk.n-1; j > 0; j--) {
                    disk.page[j][i] = disk.page[j-1][i];
                }
                disk.page[0][i] = disk.work[i];
            }else{
                disk.symbol[i] = ' ';
            }
        }
    }

    public static void LRU(Disk disk){
        boolean flag;
        for (int i = 0; i < disk.len; i++) {
            flag = true;
            int t = 0;
            if(i > 0){
                for (int j = 0; j < disk.n; j++) {
                    disk.page[j][i] = disk.page[j][i-1];
                }
            }
            for (int j = 0; j < disk.n; j++) {
                if(disk.work[i] == disk.page[j][i]){
                    flag = false;
                    break;
                }
                t++;
            }
            if(flag){
                disk.symbol[i] = '+';
                disk.out[i] = disk.page[disk.n-1][i];
                for (int j = disk.n-1; j > 0; j--) {
                    disk.page[j][i] = disk.page[j-1][i];
                }
                disk.page[0][i] = disk.work[i];
            }else{
                disk.symbol[i] = ' ';
                for (int j = t; j > 0; j--) {
                    disk.page[j][i] = disk.page[j-1][i];
                }
                disk.page[0][i] = disk.work[i];
            }

        }
    }

    public static void print(Disk disk){
        System.out.println("作业名:"+disk.name);
        System.out.println("作业调度过程:");
        System.out.print(" ");
        for (int i = 0; i < disk.len; i++) {
            System.out.print("\t"+i);
        }
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i < disk.len; i++) {
            System.out.print("\t"+disk.work[i]);
        }
        System.out.println();
        for (int i = 0; i < disk.n; i++) {
            System.out.print(i);
            for (int j = 0; j < disk.len; j++) {
                if (disk.page[i][j] == -1) {
                    System.out.print("\t ");
                    continue;
                }
                System.out.print("\t"+disk.page[i][j]);
            }
            System.out.println();
        }
        System.out.print(" ");
        for (int i = 0; i < disk.len; i++) {
            System.out.print("\t"+disk.symbol[i]);
        }
        System.out.println();
        for (int j = 0; j < disk.len; j++) {
            if (disk.out[j] == -1) {
                System.out.print("\t ");
                continue;
            }
            System.out.print("\t"+disk.out[j]);
        }
        System.out.println();
        int len = 0;
        for (int i = 0; i < disk.len; i++) {
            if ('+' == disk.symbol[i]){
                len++;
            }
        }
        System.out.print("缺页中断率为:"+String.format("%-4.2f",((float)len/disk.len)*100)+"\n");
    }
}
