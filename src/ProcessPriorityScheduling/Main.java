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
            System.out.print("请输入进程数:");
            int num = s.nextInt();
            System.out.println("请输入"+num+"个进程的:");
            System.out.println("ID号  名字  优先级  到达时间  执行时间(分钟):");//输入用空格分隔
            for (int i = 0; i < num; i++) {
                Process p = new Process();
                p.setId(s.nextInt());
                p.setName(s.next());
                p.setGood(s.nextInt());
                time t = new time();
                t.initTime(s.next());
                p.setArrive(t);
                p.setZx(s.nextInt());
                list.add(p);
            }
            //调度算法
            System.out.println(" ");
            System.out.println("模拟进程优先级调度过程输出结果:");
            System.out.println("ID号  名字  优先级  到达时间  执行时间(分钟)  开始时间  完成时间  周转时间(分钟)  带权周转时间(系数):");
            int loc,good,temp;
            int tag = 0;
            float sumZz = 0;
            float sumZzxs = 0;
            time finish = new time();
            finish.setHour(0);
            finish.setMin(0);
            while(list.size() != 0){
                // 选择先到达且优先级较高的的进入队列
                loc = 0;
                if(tag == 0){
                    for (int i = 1; i < list.size(); i++) {
                        if(time.sub(list.get(loc).getArrive().toString(),list.get(i).getArrive().toString()) < 0 ){
                            loc = i;
                        }
                    }
                    tag++;
                }else{
                    good = 0;
                    temp = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if(time.sub(finish.toString(),list.get(i).getArrive().toString()) < 0 && good-list.get(i).getGood() < 0){
                            loc = i;
                            good = list.get(loc).getGood();
                            temp = 1;
                        }
                    }
                    if (temp == 0){
                        for (int i = 1; i < list.size(); i++) {
                            if(time.sub(list.get(loc).getArrive().toString(),list.get(i).getArrive().toString()) < 0 ){
                                loc = i;
                            }
                        }
                    }
                }
                // 计算时间有关的信息
                if(time.sub(list.get(loc).getArrive().toString(),finish.toString()) >= 0){
                    hour = finish.getHour();
                    min = finish.getMin();
                    time t1 = new time();
                    t1.setHour(hour);
                    t1.setMin(min);
                    list.get(loc).setStart(t1);
                }else{
                    hour = list.get(loc).getArrive().getHour();
                    min = list.get(loc).getArrive().getMin();
                    time t1 = new time();
                    t1.setHour(hour);
                    t1.setMin(min);
                    list.get(loc).setStart(t1);
                }
                hour += (min+list.get(loc).getZx())/60;
                min = (min+list.get(loc).getZx())%60;
                time t2 = new time();
                t2.setHour(hour);
                t2.setMin(min);
                finish = t2;
                list.get(loc).setFinish(t2);
                zz = time.sub(list.get(loc).getArrive().getHour()+":"+list.get(loc).getArrive().getMin(),
                        list.get(loc).getFinish().getHour()+":"+list.get(loc).getFinish().getMin());
                list.get(loc).setZz(zz);
                list.get(loc).setZzxs((float)list.get(loc).getZz()/list.get(loc).getZx());
                sumZz += list.get(loc).getZz();
                sumZzxs += list.get(loc).getZzxs();
                System.out.println(String.format("%-6d",list.get(loc).getId())
                        +String.format("%-6s",list.get(loc).getName())
                        +String.format("%-8d",list.get(loc).getGood())
                        +String.format("%-10s",list.get(loc).getArrive().toString())
                        +String.format("%-13s",list.get(loc).getZx()+"(分钟)")
                        +String.format("%-9s",list.get(loc).getStart().toString())
                        +String.format("%-10s",list.get(loc).getFinish().toString())
                        +String.format("%-16s",list.get(loc).getZz()+"(分钟)")
                        +String.format("%-4.2f",list.get(loc).getZzxs()));
                list.remove(loc);
            }
            System.out.println(String.format("%-56s","系统平均周转时间为:")+String.format(" %-4.2f",sumZz/num));
            System.out.println(String.format("%-72s","系统带权平均周转时间为: ")+String.format("%-4.2f",sumZzxs/num));
            System.out.println("=====================================================================================");
            System.out.println("是否继续进程调度实验,开始实验:1，结束实验:0");
            flag = s.nextInt();
        }
        System.out.println("进程调度实验结束!!!");
        s.close();
    }
}
/*
5001	p1	1  9:40	    20
5004	p4	4  10:10	10
5005	p5	3  10:05	30
5012	p2	3  9:55	    15
5003	p3	2  9:45	    25
* */
/*
5001  p1  1  19:40  20
5002  p4  2  10:10  10
5003  p5  3  10:05  30
5004  p2  4  09:55   15
5005  p3  5  09:45   25
5006  p6  6  11:40  20
5007  p8  7  12:10  10
5008  p9  8  13:05  30
5009  p10 9  19:55  15
5010  p7  10 07:15   15
* */