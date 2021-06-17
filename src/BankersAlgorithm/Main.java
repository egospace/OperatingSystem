package BankersAlgorithm;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*
         * 读入程序
         * */
        Scanner s = new Scanner(System.in);
        System.out.print("请输入资源种类: ");
        int N = s.nextInt();
        System.out.print("请输入进程数: ");
        int M = s.nextInt();
        OS os = new OS();
        os.init(N,M);
        System.out.print("请输入"+N+"类资源初始化的资源数: ");
        for (int i = 0; i < N; i++) {
            os.available[i] = s.nextInt();
        }
        System.out.println("请输入"+M+"个进程的:");
        System.out.println("进程名\t\t\t\t最大需求量:");
        System.out.print("      \t\t\t\t");
        for (int i = 0; i < N; i++) {
            System.out.print((char)(i+65)+" ");
        }
        System.out.println();
        for (int i = 0; i < M; i++) {
            System.out.print("进程p["+(i+1)+"]\t\t\t");
            for (int j = 0; j < N; j++) {
                os.max[i][j] = s.nextInt();
                os.need[i][j] = os.max[i][j];
                os.allocate[i][j] = 0;
            }
            os.flag[i] = 0;
        }

        System.out.println("请输入"+M+"个进程的:");
        System.out.println("进程名\t\t\t\t第一次申请量:");
        System.out.print("      \t\t\t\t");
        for (int i = 0; i < N; i++) {
            System.out.print((char)(i+65)+" ");
        }
        System.out.println();
        for (int i = 0; i < M; i++) {
            System.out.print("进程p["+(i+1)+"]\t\t\t");
            for (int j = 0; j < N; j++) {
                os.request[i][j] = s.nextInt();
            }
            while(os.check(i) == 0){
                System.out.println("请重新输入这个进程的申请信息");
                for (int j = 0; j < N; j++) {
                    os.request[i][j] = s.nextInt();
                }
            }
        }
        os.print();
        System.out.print("是否需要再申请资源?(y/n)");
        int i;
        int temp;
        while ("y".equals(s.next())){
            System.out.print("请输入进程编号(1-"+M+")P: ");
            i = s.nextInt();
            System.out.print("请输入进程P["+i+"]对"+N+"类资源的申请: ");
            for (int j = 0; j < N; j++) {
                os.request[i-1][j] = s.nextInt();
            }
            temp = os.check(i-1);
            if(temp == 0){
                System.out.println("请重新输入这个进程的申请信息");
                for (int j = 0; j < N; j++) {
                    os.request[i-1][j] = s.nextInt();
                }
                temp = os.check(i-1);
            }
            if(temp == 2){
                os.print();
            }
            System.out.print("是否需要再申请资源?(y/n) ");
        }
    }
}
