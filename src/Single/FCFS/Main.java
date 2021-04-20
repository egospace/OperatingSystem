package Single.FCFS;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("是否开始进程调度实验,开始实验:1，结束实验:0");
        int flag = s.nextInt();
        while (flag == 1){
            ArrayList<Process> list = new ArrayList<>();
            int zz;
            int hour;
            int min;
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
                list.add(p);
            }
            list.sort(Process::compareTo);
            //调度算法
            System.out.println(" ");
            System.out.println("模拟进程FCFS调度过程输出结果:");
            System.out.println("名字  入井时间  运行时间(分钟)  作业调度时间 作业调度等待时间  进程调度时间  进程调度等待时间 完成时间 周转时间 带权周转时间:");
            int loc = 0;
            float sumZz = 0;
            float sumZzxs = 0;
            time finish = new time();
            finish.setHour(0);
            finish.setMin(0);
            while(loc < list.size()){
                // 选择先到达的进入队列
//                for (int i = 1; i < list.size(); i++) {
//                    if(time.sub(list.get(loc).arrive.toString(),list.get(i).arrive.toString()) < 0){
//                        loc = i;
//                    }
//                }
                // 计算时间有关的信息
                if(time.sub(list.get(loc).arrive.toString(),finish.toString()) >= 0){
                    hour = finish.getHour();
                    min = finish.getMin();
                    time t1 = new time();
                    t1.setHour(hour);
                    t1.setMin(min);
                    list.get(loc).jobTime = t1;
                    list.get(loc).jobWait = 0;
                    list.get(loc).processTime = t1;
                    list.get(loc).processWait = 0;
                    list.get(loc).jobWait = time.sub(list.get(loc).arrive.toString(),finish.toString());
                }else{
                    hour = list.get(loc).arrive.getHour();
                    min = list.get(loc).arrive.getMin();
                    time t1 = new time();
                    t1.setHour(hour);
                    t1.setMin(min);
                    list.get(loc).jobTime = t1;
                    list.get(loc).processTime = t1;
                    list.get(loc).processWait = 0;
                }
                hour += (min+list.get(loc).zx)/60;
                min = (min+list.get(loc).zx)%60;
                time t2 = new time();
                t2.setHour(hour);
                t2.setMin(min);
                finish = t2;
                list.get(loc).finish = t2;
                zz = time.sub(list.get(loc).arrive.getHour()+":"+list.get(loc).arrive.getMin(),
                        list.get(loc).finish.getHour()+":"+list.get(loc).finish.getMin());
                list.get(loc).zz = zz;
                list.get(loc).zzxs = (float)list.get(loc).zz/list.get(loc).zx;
                sumZz += list.get(loc).zz;
                sumZzxs += list.get(loc).zzxs;
                System.out.println(
                        String.format("%-6s",list.get(loc).name)
                        +String.format("%-10s",list.get(loc).arrive.toString())
                        +String.format("%-13s",list.get(loc).zx+"(分钟)")
                        +String.format("%-15s",list.get(loc).jobTime.toString())
                        +String.format("%-12s",list.get(loc).jobWait+"(分钟)")
                        +String.format("%-16s",list.get(loc).processTime.toString())
                        +String.format("%-9s",list.get(loc).processWait+"(分钟)")
                        +String.format("%-7s",list.get(loc).finish.toString())
                        +String.format("%-10s",list.get(loc).zz+"(分钟)")
                        +String.format("%-4.4f",list.get(loc).zzxs)+"(系数)");
//                list.remove(loc);
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