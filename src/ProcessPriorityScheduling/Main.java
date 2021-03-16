package ProcessPriorityScheduling;

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
            int sumZz = 0;
            int sumZzxs = 0;
            System.out.print("请输入进程数:");
            int num = s.nextInt();
            System.out.println("请输入"+num+"个进程的:");
            System.out.println("ID号  名字  到达时间  执行时间(分钟):");//输入用空格分隔
            for (int i = 0; i < num; i++) {
                Process p = new Process();
                p.setId(s.nextInt());
                p.setName(s.next());
                time t = new time();
                t.initTime(s.next());
                p.setArrive(t);
                p.setZx(s.nextInt());
                list.add(p);
            }
            list.sort(Process::compareTo);
            for (int i = 0; i < num; i++) {
                if(i == 0){
                    hour = list.get(i).getArrive().getHour();
                    min = list.get(i).getArrive().getMin();
                    time t1 = new time();
                    t1.setHour(hour);
                    t1.setMin(min);
                    list.get(i).setStart(t1);
                }else{
                    hour = list.get(i-1).getFinish().getHour();
                    min = list.get(i-1).getFinish().getMin();
                    time t1 = new time();
                    t1.setHour(hour);
                    t1.setMin(min);
                    list.get(i).setStart(t1);
                }
                hour += (min+list.get(i).getZx())/60;
                min = (min+list.get(i).getZx())%60;
                time t2 = new time();
                t2.setHour(hour);
                t2.setMin(min);
                list.get(i).setFinish(t2);
                zz = time.sub(list.get(i).getArrive().getHour()+":"+list.get(i).getArrive().getMin(),
                        list.get(i).getFinish().getHour()+":"+list.get(i).getFinish().getMin());
                list.get(i).setZz(zz);
                list.get(i).setZzxs((float)list.get(i).getZz()/list.get(i).getZx());
            }
            System.out.println(" ");
            System.out.println("模拟进程FCFS调度过程输出结果:");
            System.out.println("ID号  名字  到达时间  执行时间(分钟)  开始时间  完成时间  周转时间(分钟)  带权周转时间(系数):");
            for (int i = 0; i < num; i++) {
                System.out.println(String.format("%-6d",list.get(i).getId())
                        +String.format("%-6s",list.get(i).getName())
                        +String.format("%-10s",list.get(i).getArrive().toString())
                        +String.format("%-13s",list.get(i).getZx()+"(分钟)")
                        +String.format("%-9s",list.get(i).getStart().toString())
                        +String.format("%-10s",list.get(i).getFinish().toString())
                        +String.format("%-16s",list.get(i).getZz()+"(分钟)")
                        +String.format("%-4.2f",list.get(i).getZzxs()));
            }
            for (int i = 0; i < num; i++) {
                sumZz+=list.get(i).getZz();
                sumZzxs+=list.get(i).getZzxs();
            }
            System.out.println(String.format("%-48s","系统平均周转时间为:")+String.format(" %-4.2f",(float)sumZz/num));
            System.out.println(String.format("%-64s","系统带权平均周转时间为: ")+String.format("%-4.2f",(float)sumZzxs/num));
            System.out.println("=====================================================================================");
            System.out.println("是否继续进程调度实验,开始实验:1，结束实验:0");
            flag = s.nextInt();
        }
        System.out.println("进程调度实验结束!!!");
        s.close();
    }
}
//5001 p1 9:10 20
//5003 p2 8:10 10
//5004 p3 10:10 20