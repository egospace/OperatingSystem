package DiskShiftScheduling;

import javax.swing.*;
import java.util.Scanner;
public class main extends JFrame { //98 183 37 122 14 124 65 67
    public static void main(String[] args) {

        boolean flag = true;
        while(flag){
            switch (menu()){
                case 1:
                    FCFS();
                    break;
                case 2:
                    SSTF();
                    break;
                case 3:
                    elevator();
                    break;
                default:
                    flag = false;
                    break;
            }
        }
    }

    public static int menu(){
        System.out.println("*********************调度过程*********************");
        System.out.println("          *     1.FCFS磁盘移臂调度过程      * ");
        System.out.println("          *     2.SSTF磁盘移臂调度过程      *");
        System.out.println("          *     3.电梯调度                 *");
        System.out.println("          *     0.退出                     *");
        System.out.print("请输入选项:");
        return new Scanner(System.in).nextInt();
    }

    public static void FCFS(){
        Scanner scan = new Scanner(System.in);
        System.out.println("*********************FCFS磁盘移臂调度过程*********************");
        System.out.print("请输入访问序列的长度:");
        int len = scan.nextInt();
        System.out.print("请输入访问的柱面顺序:");
        int[] list = new int[len];
        int[] listIn = new int[len];
        int[] out = new int[len+1];
        for (int i = 0; i < len; i++) {
            list[i] = scan.nextInt();
            listIn[i] = list[i];
        }
        System.out.print("请输入正在访问的页面:");
        out[0] = scan.nextInt();
        int distance = 0;
        for (int i = 1; i < len+1; i++) {
            out[i] = list[i-1];
            distance += Math.abs(out[i]-out[i-1]);
        }
        System.out.println("移动顺序为:");
        for (int i = 0; i < len+1; i++) {
            System.out.print(out[i]+" ");
        }
        System.out.println();
        System.out.println("移动柱面为:"+distance);
        new Draw(list,out);
    }

    public static void SSTF(){
        Scanner scan = new Scanner(System.in);
        System.out.println("*********************SSTF磁盘移臂调度过程*********************");
        System.out.print("请输入访问序列的长度:");
        int len = scan.nextInt();
        System.out.print("请输入访问的柱面顺序:");
        int[] list = new int[len];
        int[] listIn = new int[len];
        int[] out = new int[len+1];
        for (int i = 0; i < len; i++) {
            list[i] = scan.nextInt();
            listIn[i] = list[i];
        }
        System.out.print("请输入正在访问的页面:");
        out[0] = scan.nextInt();
        int distance = 0;
        int min;
        int temp;
        for (int i = 1; i < len+1; i++) {
            temp = 0;
            min = 999999999;
            for (int j = 0; j < len; j++) {
                if (list[j] == -1) continue;
                if(Math.abs(out[i-1]-list[j]) < min){
                    min = Math.abs(out[i-1]-list[j]);
                    temp = j;
                }
            }
            out[i] = list[temp];
            list[temp] = -1;
            distance += Math.abs(out[i]-out[i-1]);
        }
        System.out.println("最短优先的顺序为:");
        for (int i = 0; i < len+1; i++) {
            System.out.print(out[i]+" ");
        }
        System.out.println();
        System.out.println("移动柱面为:"+distance);
        new Draw(listIn,out);
    }

    public static void elevator(){
        Scanner scan = new Scanner(System.in);
        System.out.println("*********************电梯调度过程*********************");
        System.out.print("请输入访问序列的长度:");
        int len = scan.nextInt();
        System.out.print("请输入访问的柱面顺序:");
        int[] list = new int[len];
        int[] out = new int[len+1];
        int[] outIn = new int[len+1];
        for (int i = 0; i < len; i++) {
            list[i] = scan.nextInt();
        }
        System.out.print("请输入正在访问的页面:");
        out[0] = scan.nextInt();
        outIn[0] = out[0];
        int distance = 0;
        int distanceIn = 0;
        int min;
        int temp;
        int m;
        //排序
        for (int i = 0; i < len; i++) {
            min = list[i];
            temp = i;
            for (int j = i+1; j < len; j++) {
                if(min > list[j]){
                    min = list[j];
                    temp = j;
                }
            }
            m = list[i];
            list[i] = list[temp];
            list[temp] = m;
        }
        int k = 0;
        while (out[0] - list[k] > 0){
            k++;
        }
        int t = 1;
        // 从外向里
        for (int j = k; j < len; j++) {
            out[t] = list[j];
            distance += Math.abs(out[t]-out[t-1]);
            t++;
        }
        for (int j = k-1; j >= 0; j--) {
            out[t] = list[j];
            distance += Math.abs(out[t]-out[t-1]);
            t++;
        }
        // 从里向外
        t = 1;
        for (int j = k-1; j >= 0; j--) {
            outIn[t] = list[j];
            distanceIn += Math.abs(outIn[t]-outIn[t-1]);
            t++;
        }
        for (int j = k; j < len; j++) {
            outIn[t] = list[j];
            distanceIn += Math.abs(outIn[t]-outIn[t-1]);
            t++;
        }
        System.out.println("由里向外移动的顺序为:");
        for (int i = 0; i < len+1; i++) {
            System.out.print(outIn[i]+" ");
        }
        System.out.println();
        System.out.println("移动柱面为:"+distanceIn);
        new Draw(list,outIn);
        System.out.println("由外向里移动的顺序为:");
        for (int i = 0; i < len+1; i++) {
            System.out.print(out[i]+" ");
        }
        System.out.println();
        System.out.println("移动柱面为:"+distance);
        new Draw(list,out);
    }
}
