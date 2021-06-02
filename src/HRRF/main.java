package HRRF;

import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("是否开始进程调度实验,开始实验:1，结束实验:0");
        int flag = s.nextInt();
        while (flag == 1){
            ArrayList<Process> list = new ArrayList<>();
            int zz;
            int hour = 0;
            int min = 0;
            System.out.println("=====================================================================================");
            System.out.print("请输入进程数:");
            int num = s.nextInt();
            System.out.println("请输入"+num+"个进程的:");
            System.out.println("作业名  入井时间  运行时间:");//输入用空格分隔
            for (int i = 0; i < num; i++) {
                Process p = new Process();
                p.name = s.next();
                time t = new time();
                t.initTime(s.next());
                p.arrive = t;
                p.zx = s.nextInt();
                p.finished = 0;
                list.add(p);
            }
            list.sort(Process::compareTo1);
            //调度算法
            System.out.println(" ");
            System.out.println("模拟进程HRRF调度过程输出结果:");
            System.out.println("名字  入井时间  运行时间(分钟)  作业调度时间 作业调度等待时间  进程调度时间  进程调度等待时间 完成时间 周转时间 带权周转时间:");
            int loc = 0;
            float sumZz = 0;
            float sumZzxs = 0;
            time finish = new time();
            finish.setHour(0);
            finish.setMin(0);
            int len = list.size()-1;
            while(loc < list.size()){
                for (int i = loc; i < list.size(); i++) {
                    // 计算时间有关的信息
                    if(time.sub(list.get(i).arrive.toString(),finish.toString()) >= 0){
                        hour = finish.getHour();
                        min = finish.getMin();
                        time t1 = new time();
                        t1.setHour(hour);
                        t1.setMin(min);
                        list.get(i).jobTime = t1;
                        list.get(i).jobWait = 0;
                        list.get(i).processTime = t1;
                        list.get(i).processWait = 0;
                        list.get(i).jobWait = time.sub(list.get(i).arrive.toString(),finish.toString());
                        list.get(i).response =  ((float)list.get(i).jobWait+list.get(i).zx)/list.get(i).zx;
                    }else{
                        hour = list.get(i).arrive.getHour();
                        min = list.get(i).arrive.getMin();
                        time t1 = new time();
                        t1.setHour(hour);
                        t1.setMin(min);
                        list.get(i).jobTime = t1;
                        list.get(i).processTime = t1;
                        list.get(i).processWait = 0;
                        list.get(i).response = 1;
                        if(i == 0 ){
                            list.get(i).response = 9999;
                            break;
                        }
                    }
                }
                list.sort(Process::compareTo);
                hour += (min+list.get(len).zx)/60;
                min = (min+list.get(len).zx)%60;
                time t2 = new time();
                t2.setHour(hour);
                t2.setMin(min);
                finish = t2;
                list.get(len).finish = t2;
                zz = time.sub(list.get(len).arrive.getHour()+":"+list.get(len).arrive.getMin(),
                        list.get(len).finish.getHour()+":"+list.get(len).finish.getMin());
                list.get(len).zz = zz;
                list.get(len).zzxs = (float)list.get(len).zz/list.get(len).zx;
                sumZz += list.get(len).zz;
                sumZzxs += list.get(len).zzxs;
                System.out.println(
                        String.format("%-6s",list.get(len).name)
                                +String.format("%-10s",list.get(len).arrive.toString())
                                +String.format("%-13s",list.get(len).zx+"(分钟)")
                                +String.format("%-15s",list.get(len).jobTime.toString())
                                +String.format("%-12s",list.get(len).jobWait+"(分钟)")
                                +String.format("%-16s",list.get(len).processTime.toString())
                                +String.format("%-9s",list.get(len).processWait+"(分钟)")
                                +String.format("%-7s",list.get(len).finish.toString())
                                +String.format("%-10s",list.get(len).zz+"(分钟)")
                                +String.format("%-4.4f",list.get(len).zzxs)+"(系数)");
                list.get(len).finished = 1;
                list.get(len).response = -1;
                list.sort(Process::compareTo);
                loc++;
            }
            System.out.println(String.format("%-84s","系统平均周转时间为:")+String.format(" %-4.2f",sumZz/num));
            System.out.println(String.format("%-95s","系统带权平均周转时间为: ")+String.format("%-4.4f",sumZzxs/num));
            System.out.println("=====================================================================================");
            System.out.println("是否继续进程调度实验,开始实验:1，结束实验:0");
            flag = s.nextInt();
        }
        System.out.println("进程调度实验结束!!!");
        s.close();
    }
}
/*
JOB1 8:00 120
JOB2 8:50 50
JOB3 9:00 10
JOB4 9:50 20
*/