package MUL_FCFS;




import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入你需要创建的进程数:");
        int processNum = scan.nextInt();
        System.out.print("请输入主存大小和磁带机总数:");
        int size = scan.nextInt();
        int TapeNum = scan.nextInt();
        ArrayList<Disk> list = new ArrayList<>();
        System.out.println("=====================================================================================");
        System.out.println("请依次输入:");
        System.out.println("作业名\t入井时间\t运行时间\t主存需求量\t磁带机需求量:");//输入用空格分隔
        for (int i = 0; i < processNum; i++) {
            Disk p = new Disk();
            p.name = scan.next();
            time t = new time();
            t.initTime(scan.next());
            p.inTime = t;
            p.workTime = scan.nextInt();
            p.needMemory = scan.nextInt();
            p.needTape = scan.nextInt();
            p.finished = 0;
            p.flag = 0;
            time t1 =  new time();
            t1.initTime("0:00");
            p.processTime = t1;
            time t2 =  new time();
            t2.initTime("0:00");
            p.outTime = t2;
            p.zz = 0;
            p.zzxs = 0;
            list.add(p);
        }
        list.sort(Disk::compareTo);
        System.out.println("========================================作业表==========================================");
        System.out.println("作业名\t入井时间\t运行时间\t主存需求量\t磁带机需求量:");
        for (int i = 0; i < processNum; i++) {
            System.out.println(list.get(i).name+"\t"+list.get(i).inTime+"\t\t"+list.get(i).workTime+"\t\t"+list.get(i).needMemory+"\t\t\t"+list.get(i).needTape);
        }
        System.out.println("========================================选项==========================================");
        System.out.println("                                      1.FCFS");
        System.out.println("                                      2.SJF");
        System.out.println("                                      3.HRRF");
        System.out.println("请输入选项:");
        int t = scan.nextInt();
        switch (t){
            case 1:
                FCFS(list,size,TapeNum);
                break;
            case 2:
                SJF(list,size,TapeNum);
                break;
            case 3:
                HRRF(list,size,TapeNum);
                break;
            default:
                break;
        }
    }

    public static void FCFS(ArrayList<Disk> list,int size,int TapeNum){
        ArrayList<Disk> disks = new ArrayList<>();
        int i = 0;
        list.get(i).jobTime = list.get(0).inTime;
        list.get(i).flag = 1;
        list.get(i).finished = 1;
        list.get(i).surplusMemory = size - list.get(i).needMemory;
        list.get(i).surplusTape = TapeNum - list.get(i).needTape;
        disks.add(list.get(i));
        size -= disks.get(i).needMemory;
        TapeNum -= disks.get(i).needTape;
        float sumZz = 0;
        float sumZzxs = 0;
        System.out.println("模拟作业FCFS调度过程输出结果");
        while (check(disks)) {//!!!!//带权周转系数=周转间/执行时间
            System.out.println("作业名  进入时间  运行时间(分钟)  作业调度时间  进程调度时间 结束时间 周转时间 带权周转时间  占用  剩余:");
            for (int j = i; j < disks.size(); j++) {
                if("0:00".equals(disks.get(j).processTime.toString())){
                    if(j == 0){
                        disks.get(j).processTime = disks.get(j).inTime;
                    }else{
                        disks.get(j).processTime = disks.get(j-1).outTime;
                        i++;
                    }
                    disks.get(j).finished = 0;
                    disks.get(j).flag = -1;
                    size += disks.get(j).needMemory;
                    TapeNum += disks.get(j).needTape;
                    time t = new time();
                    t.initTime(time.calc(disks.get(j).processTime.toString(),disks.get(j).workTime));
                    disks.get(j).outTime = t;
                    disks.get(j).zz = time.sub(disks.get(j).inTime.toString(),disks.get(j).outTime.toString());
                    disks.get(j).zzxs = (float)disks.get(j).zz / disks.get(j).workTime;
                    sumZz += disks.get(j).zz;
                    sumZzxs += disks.get(j).zzxs;
                    break;
                }
            }
            for (int j = 0; j < disks.size(); j++) {
                System.out.println(
                        String.format("%-9s",disks.get(j).name)
                                +String.format("%-12s",disks.get(j).inTime.toString())
                                +String.format("%-13s",disks.get(j).workTime)
                                +String.format("%-13s",disks.get(j).jobTime.toString())
                                +String.format("%-10s",disks.get(j).processTime.toString())
                                +String.format("%-10s",disks.get(j).outTime.toString())
                                +String.format("%-8s",disks.get(j).zz)
                                +String.format("%-10.4f",disks.get(j).zzxs)
                                +String.format("%-3s",disks.get(j).needMemory)
                                +String.format("%-2s",disks.get(j).needTape)
                                +String.format("%-3s",disks.get(j).surplusMemory)
                                +String.format("%-4s",disks.get(j).surplusTape)
                );
            }
            for (int j = 0;j < list.size();j++) {
                if(list.get(j).flag == 0){
                    if(time.sub(list.get(j).inTime.toString(),disks.get(i).outTime.toString()) > 0){
                        if(list.get(j).needMemory <= size && list.get(j).needTape <= TapeNum){
                            list.get(j).jobTime = disks.get(i).outTime;
                            list.get(j).surplusMemory = size - list.get(j).needMemory;
                            list.get(j).surplusTape = TapeNum - list.get(j).needTape;
                            list.get(j).finished = 1;
                            list.get(j).flag = 1;
                            size -= list.get(j).needMemory;
                            TapeNum -= list.get(j).needTape;
                            disks.add(list.get(j));
                        }
                    }
                }
            }
        }
        System.out.println("系统平均周转时间为:"+String.format(" %-4.4f",sumZz/disks.size())+"分钟");
        System.out.println("系统带权平均周转时间为: "+String.format("%-4.4f",sumZzxs/disks.size())+"分钟");
    }

    public static void SJF(ArrayList<Disk> list,int size,int TapeNum){
        ArrayList<Disk> disks = new ArrayList<>();
        int i = 0;
        list.get(i).jobTime = list.get(0).inTime;
        list.get(i).flag = 1;
        list.get(i).finished = 1;
        list.get(i).surplusMemory = size - list.get(i).needMemory;
        list.get(i).surplusTape = TapeNum - list.get(i).needTape;
        disks.add(list.get(i));
        size -= disks.get(i).needMemory;
        TapeNum -= disks.get(i).needTape;
        float sumZz = 0;
        float sumZzxs = 0;
        System.out.println("模拟作业SJF调度过程输出结果");
        while (check(disks)) {//!!!!//带权周转系数=周转间/执行时间
            System.out.println("作业名  进入时间  运行时间(分钟)  作业调度时间  进程调度时间 结束时间 周转时间 带权周转时间  占用  剩余:");
            for (int j = i; j < disks.size(); j++) {
                if("0:00".equals(disks.get(j).processTime.toString())){
                    if(j == 0){
                        disks.get(j).processTime = disks.get(j).inTime;
                    }else{
                        disks.get(j).processTime = disks.get(j-1).outTime;
                        i++;
                    }
                    disks.get(j).finished = 0;
                    disks.get(j).flag = -1;
                    size += disks.get(j).needMemory;
                    TapeNum += disks.get(j).needTape;
                    time t = new time();
                    t.initTime(time.calc(disks.get(j).processTime.toString(),disks.get(j).workTime));
                    disks.get(j).outTime = t;
                    disks.get(j).zz = time.sub(disks.get(j).inTime.toString(),disks.get(j).outTime.toString());
                    disks.get(j).zzxs = (float)disks.get(j).zz / disks.get(j).workTime;
                    sumZz += disks.get(j).zz;
                    sumZzxs += disks.get(j).zzxs;
                    break;
                }
            }
            for (int j = 0; j < disks.size(); j++) {
                System.out.println(
                        String.format("%-9s", disks.get(j).name)
                                + String.format("%-12s", disks.get(j).inTime.toString())
                                + String.format("%-13s", disks.get(j).workTime)
                                + String.format("%-13s", disks.get(j).jobTime.toString())
                                + String.format("%-10s", disks.get(j).processTime.toString())
                                + String.format("%-10s", disks.get(j).outTime.toString())
                                + String.format("%-8s", disks.get(j).zz)
                                + String.format("%-10.4f", disks.get(j).zzxs)
                                + String.format("%-3s", disks.get(j).needMemory)
                                + String.format("%-2s", disks.get(j).needTape)
                                + String.format("%-3s", disks.get(j).surplusMemory)
                                + String.format("%-4s", disks.get(j).surplusTape)
                );
            }
            list.sort(Disk::compareTo2);
            for (int j = 0;j < list.size();j++) {
                if(list.get(j).flag == 0){
                    if(list.get(j).needMemory <= size && list.get(j).needTape <= TapeNum){
                        list.get(j).jobTime = disks.get(i).outTime;
                        list.get(j).surplusMemory = size - list.get(j).needMemory;
                        list.get(j).surplusTape = TapeNum - list.get(j).needTape;
                        list.get(j).finished = 1;
                        list.get(j).flag = 1;
                        size -= list.get(j).needMemory;
                        TapeNum -= list.get(j).needTape;
                        disks.add(list.get(j));
                    }
                }
            }
        }
        System.out.println("系统平均周转时间为:"+String.format(" %-4.4f",sumZz/disks.size())+"分钟");
        System.out.println("系统带权平均周转时间为: "+String.format("%-4.4f",sumZzxs/disks.size())+"分钟");
    }

    public static void HRRF(ArrayList<Disk> list,int size,int TapeNum){
        ArrayList<Disk> disks = new ArrayList<>();
        int i = 0;
        list.get(i).jobTime = list.get(0).inTime;
        list.get(i).flag = 1;
        list.get(i).finished = 1;
        list.get(i).surplusMemory = size - list.get(i).needMemory;
        list.get(i).surplusTape = TapeNum - list.get(i).needTape;
        disks.add(list.get(i));
        size -= disks.get(i).needMemory;
        TapeNum -= disks.get(i).needTape;
        float sumZz = 0;
        float sumZzxs = 0;
        System.out.println("模拟作业FCFS调度过程输出结果");
        while (check(disks)) {//!!!!//带权周转系数=周转间/执行时间
            System.out.println("作业名  进入时间  运行时间(分钟)  作业调度时间  进程调度时间 结束时间 周转时间 带权周转时间  占用  剩余:");
            for (int j = i; j < disks.size(); j++) {
                if("0:00".equals(disks.get(j).processTime.toString())){
                    if(j == 0){
                        disks.get(j).processTime = disks.get(j).inTime;
                    }else{
                        disks.get(j).processTime = disks.get(j-1).outTime;
                        i++;
                    }
                    disks.get(j).finished = 0;
                    disks.get(j).flag = -1;
                    size += disks.get(j).needMemory;
                    TapeNum += disks.get(j).needTape;
                    time t = new time();
                    t.initTime(time.calc(disks.get(j).processTime.toString(),disks.get(j).workTime));
                    disks.get(j).outTime = t;
                    disks.get(j).zz = time.sub(disks.get(j).inTime.toString(),disks.get(j).outTime.toString());
                    disks.get(j).zzxs = (float)disks.get(j).zz / disks.get(j).workTime;
                    disks.get(j).response = -1;
                    sumZz += disks.get(j).zz;
                    sumZzxs += disks.get(j).zzxs;
                    break;
                }
            }
            for (int j = 0; j < disks.size(); j++) {
                System.out.println(
                        String.format("%-9s",disks.get(j).name)
                                +String.format("%-12s",disks.get(j).inTime.toString())
                                +String.format("%-13s",disks.get(j).workTime)
                                +String.format("%-13s",disks.get(j).jobTime.toString())
                                +String.format("%-10s",disks.get(j).processTime.toString())
                                +String.format("%-10s",disks.get(j).outTime.toString())
                                +String.format("%-8s",disks.get(j).zz)
                                +String.format("%-10.4f",disks.get(j).zzxs)
                                +String.format("%-3s",disks.get(j).needMemory)
                                +String.format("%-2s",disks.get(j).needTape)
                                +String.format("%-3s",disks.get(j).surplusMemory)
                                +String.format("%-4s",disks.get(j).surplusTape)
                );
            }
            for (int k = 0; k < list.size(); k++) {
                if(list.get(k).flag == 0){
                    list.get(k).response =  ((float)time.sub(list.get(k).inTime.toString(),disks.get(i).outTime.toString())+list.get(k).workTime)/list.get(k).workTime;
                }
            }
            list.sort(Disk::compareTo3);
            for (int j = list.size()-1;j >= i;j--) {
                if(list.get(j).flag == 0){
                    if(time.sub(list.get(j).inTime.toString(),disks.get(i).outTime.toString()) > 0){
                        if(list.get(j).needMemory <= size && list.get(j).needTape <= TapeNum){
                            list.get(j).jobTime = disks.get(i).outTime;
                            list.get(j).surplusMemory = size - list.get(j).needMemory;
                            list.get(j).surplusTape = TapeNum - list.get(j).needTape;
                            list.get(j).finished = 1;
                            list.get(j).flag = 1;
                            size -= list.get(j).needMemory;
                            TapeNum -= list.get(j).needTape;
                            disks.add(list.get(j));
                        }
                    }
                }
            }
        }
        System.out.println("系统平均周转时间为:"+String.format(" %-4.4f",sumZz/disks.size())+"分钟");
        System.out.println("系统带权平均周转时间为: "+String.format("%-4.4f",sumZzxs/disks.size())+"分钟");
    }

    public static boolean check(ArrayList<Disk> disks){
        for (int i = 0; i < disks.size(); i++) {
            if(disks.get(i).flag != -1){
                return true;
            }
        }
        return false;
    }
}

/**
 job1    10:00      40      35           3
 job2    10:10      30      70           1
 job3    10:15      20      50           3
 job4    10:35      10      25           2
 job5    10:40      5       20           2
 * */