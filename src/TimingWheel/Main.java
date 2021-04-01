package TimingWheel;
/*
* 本实验我采用了两个队列进行操作，一个是等待队列（获取用户的输入，并排序），
* 还有一个队列是就绪队列（索引队列，用来索引等待队列里面的进程），
* 就绪队列里面的front和rear就有点像一个哨兵，只有在front和rear之间的元素才在队列当中，front之前的元素，
* 代表是执行结束了的元素，rear之后的元素代表是等待队列里面元素
* */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        * 读入程序
        * */
        Scanner s = new Scanner(System.in);
        System.out.println("是否开始进程调度实验,开始实验:1，结束实验:0");
        int flag = s.nextInt();
        while (flag == 1){
            ArrayList<Process> list = new ArrayList<>();
            int zz;
            System.out.println("=====================================================================================");
            System.out.print("请输入进程数:");
            int num = s.nextInt();
            System.out.println("请输入"+num+"个进程的:");
            System.out.println("ID号  名字  到达时间  执行时间(分钟):");//输入用空格分隔
            for (int i = 0; i < num; i++) {
                Process p = new Process();
                p.setId(s.nextInt());
                p.setName(s.next());
                time t = new time();
                time t1 = new time();
                t.initTime(s.next());
                p.setArrive(t);
                t1.setHour(t.getHour());
                t1.setMin(t.getMin());
                p.setArr(t1);
                p.setZx(s.nextInt());
                p.setFlag(0);
                list.add(p);
            }
            /*
             * 所有进程按照到达时间排序的算法，这里Process类继承了Comparable类，
             * 然后实现了compareTo方法,然后调用sort方法该进程类进行排序
             * */
            list.sort(Process::compareTo);
            ArrayList<Integer> locIndex = new ArrayList<>(); // 就绪队列的索引列表
            int front = 0; //就绪队列里面的头指针
            int rear = 0; //就绪队列里面的尾指针
            int loc = 0; //读取到了初始队列中第几个元素
            int tag = 0;//判断是否需要进行初始化操作
            float sumZz = 0;
            float sumZzxs = 0;
            int count = 0; //记录是第几轮
            int fl = 0; //标志，判断是否有元素出队，如果有则返回1，如果没有则为0
            time finish = new time(); //程序的初始化操作（定义一个初始化的完成时间）
            finish.setHour(0);
            finish.setMin(0);
            while(isFlag(list) || front <= list.size()){
                // 选择是否进队
                if(tag == 0){ //就绪队列为空的时候，选择该方法进队
                    if(loc < list.size()){
                        if(time.sub(list.get(loc).getArrive().toString(),finish.toString()) < 0){
                            finish.setHour(list.get(loc).getArrive().getHour());
                            finish.setMin(list.get(loc).getArrive().getMin());
                            list.get(loc).setArr(list.get(loc).getArrive());
                            time t = new time();
                            t.setHour(finish.getHour());
                            t.setMin(finish.getMin());
                            list.get(loc).setStart(t);
                            locIndex.add(loc);
                            loc++;
                            rear++;
                        }
                        tag = 1;
                    }
                    time t1 = new time();
                    t1.setHour(0);
                    t1.setMin(0);
                }else{// 就绪队列不为空的时候，选择下述方法进队
                    count++;// 计数器，用来记录是第几轮输出
                    if(loc < list.size()){ //进队，只要到达时间比完成时间小的进程全部进队
                        int i;
                        for (i = loc; i < list.size(); i++) {
                            if(time.sub(list.get(i).getArrive().toString(),finish.toString()) < 0){
                                break;
                            }
                            locIndex.add(i);
                            time t = new time();
                            t.setHour(0);
                            t.setMin(0);
                            list.get(i).setStart(t);
                        }
                        loc = i;
                        rear = loc;
                    }
                    int t;//用于判断是否有进程刚刚出队，如果有的话，我们还需要输出刚刚出队的那个进程，如果没有则输出当前进程的值
                    if(fl == 1){
                        t = front-1;
                    }else{
                        t = front;
                    }
                    // 输出程序
                    System.out.println(" ");
                    System.out.println("第"+count+"轮执行和就绪队列结果:");
                    System.out.println("ID号  名字  到达时间  总执行时间(分钟)  当前开始时间  已完成时间(分钟)  剩余完成时间(分钟):");
                    for (int j= t ; j < rear; j++) {
                        System.out.println(String.format("%-6d",list.get(locIndex.get(j)).getId())
                                +String.format("%-6s",list.get(locIndex.get(j)).getName())
                                +String.format("%-10s",list.get(locIndex.get(j)).getArrive().toString())
                                +String.format("%-15s",list.get(locIndex.get(j)).getZx()+"(分钟)")
                                +String.format("%-13s",list.get(locIndex.get(j)).getStart())
                                +String.format("%-16s",list.get(locIndex.get(j)).getHave_finished()+"(分钟)")
                                +String.format("%-4d",(list.get(locIndex.get(j)).getZx())-list.get(locIndex.get(j)).getHave_finished()));
                    }
                    // 判断是否需要把front所在位置的原始交换到最后，如果是完成了的进程则不需要进行交换，front直接后移就好了
                    if(fl == 1){
                        fl = 0;
                    }else{
                        time t2 = new time();//把使用过后的start进行初始化操作
                        t2.initTime("0:00");
                        list.get(locIndex.get(front)).setStart(t2);
                        locIndex.add(locIndex.get(front));
                        locIndex.remove(front);
                    }
                }
                // 判断是否需要退出循环
                if(front >= list.size()) break;
                //判断是否需要跳过本次循环，并对本次循环进行初始化，原因是当我们碰到就绪队列中的全部进程都执行，
                // 但是等待队列还有进程进行等待的时候就需要进行一次初始化，相当于创建一个新的就绪队列
                if(front >=locIndex.size()) {
                    tag = 0;
                    finish.setHour(0);
                    finish.setMin(0);
                    continue;
                }
                // 记录首次执行的时间
                if (list.get(locIndex.get(front)).getFirst() == null){
                    time t1 = new time();
                    t1.setHour(finish.getHour());
                    t1.setMin(finish.getMin());
                    list.get(locIndex.get(front)).setFirst(t1);
                }
                /*
                * 与时间有关的计算，首先先判断执行时间减去完成时间是否小于8，如果小于8，则代表该进程已经计算完成，
                * 将其的flag赋值为1，并计算相关时间，并把fl（记录是否有进程刚刚出队的一个标志符）赋值为1，代表有。
                * 同时front++;就绪队列的指针向后移
                * 如果大于8，则代表进程还没有执行完成，那么就执行一个完整的时间片的长度，并计算有关的时间。
                * */
                if (list.get(locIndex.get(front)).getZx()-list.get(locIndex.get(front)).getHave_finished() < 8){
                    list.get(locIndex.get(front)).setFlag(1);
                    list.get(locIndex.get(front)).getStart().setHour(finish.getHour());
                    list.get(locIndex.get(front)).getStart().setMin(finish.getMin());
                    String f = time.calc(list.get(locIndex.get(front)).getStart().toString(),(list.get(locIndex.get(front)).getZx()-list.get(locIndex.get(front)).getHave_finished()));
                    time t = new time();
                    t.initTime(f);
                    finish.setHour(t.getHour());
                    finish.setMin(t.getMin());
                    list.get(locIndex.get(front)).setFinish(t);
                    list.get(locIndex.get(front)).setHave_finished(list.get(locIndex.get(front)).getZx());
                    zz = time.sub(list.get(locIndex.get(front)).getArrive().getHour()+":"+list.get(locIndex.get(front)).getArrive().getMin(),
                            list.get(locIndex.get(front)).getFinish().getHour()+":"+list.get(locIndex.get(front)).getFinish().getMin());
                    list.get(locIndex.get(front)).setZz(zz);
                    list.get(locIndex.get(front)).setZzxs((float)list.get(locIndex.get(front)).getZz()/list.get(locIndex.get(front)).getZx());
                    sumZz += list.get(locIndex.get(front)).getZz();
                    sumZzxs += list.get(locIndex.get(front)).getZzxs();
                    fl = 1;
                    front++;
                }else{
                    int t = list.get(locIndex.get(front)).getHave_finished()+8;
                    list.get(locIndex.get(front)).setHave_finished(t);
                    String f = time.calc(finish.toString(),8);
                    list.get(locIndex.get(front)).getStart().setHour(finish.getHour());
                    list.get(locIndex.get(front)).getStart().setMin(finish.getMin());
                    time t1 = new time();
                    t1.initTime(f);
                    finish.setHour(t1.getHour());
                    finish.setMin(t1.getMin());
                    list.get(locIndex.get(front)).setArr(t1);
                }

            }
            // 输出其他时间有关的值
            System.out.println(" ");
            System.out.println("ID号  名字  到达时间  执行时间(分钟)  首次开始时间  完成时间  周转时间(分钟)  带权周转时间(系数):");
            for (int i = 0; i < locIndex.size(); i++) {
                System.out.println(String.format("%-6d",list.get(locIndex.get(i)).getId())
                        +String.format("%-6s",list.get(locIndex.get(i)).getName())
                        +String.format("%-10s",list.get(locIndex.get(i)).getArrive().toString())
                        +String.format("%-13s",list.get(locIndex.get(i)).getZx()+"(分钟)")
                        +String.format("%-12s",list.get(locIndex.get(i)).getFirst().toString())
                        +String.format("%-12s",list.get(locIndex.get(i)).getFinish().toString())
                        +String.format("%-16s",list.get(locIndex.get(i)).getZz()+"(分钟)")
                        +String.format("%-4.2f",list.get(locIndex.get(i)).getZzxs()));
            }
            System.out.println(String.format("%-53s","系统平均周转时间为:")+String.format(" %-4.2f",sumZz/num));
            System.out.println(String.format("%-69s","系统带权平均周转时间为: ")+String.format("%-4.2f",sumZzxs/num));
            System.out.println("=====================================================================================");
            System.out.println("是否继续进程调度实验,开始实验:1，结束实验:0");
            flag = s.nextInt();
        }
        System.out.println("进程调度实验结束!!!");
        s.close();
    }

    public static boolean isFlag(ArrayList<Process> list){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getFlag() == 0){
                return true;
            }
        }
        return false;
    }
}

/*
5001	p1	9:40	20
5004	p4	10:10	10
5005	p5	10:05	30
5002	p2	9:55	15
5003	p3	9:45	25
* */
/*
5001	p1	9:40	20
5004	p4	12:10	10
5005	p5	10:05	30
5002	p2	9:55	15
5003	p3	9:45	25
*/
/*
5009  p9  19:40   20
5005  p5  10:10   10
5004  p4  10:05   30
5003  p3  9:55    15
5002  p2  9:45    25
5006  p6  11:40   20
5007  p7  12:10   10
5008  p8  13:05   30
5010  p10 19:55   15
5001  p1  7:15    15
*/