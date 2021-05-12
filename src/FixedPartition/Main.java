package FixedPartition;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int block_num;
        int job_num;
        System.out.print("请输入系统的分区块数:");
        block_num = scan.nextInt();
        ArrayList<Disk> disks = init(block_num);
        print(disks);
        System.out.print("请输入作业的个数:");
        job_num = scan.nextInt();
        job_init(job_num,disks);
        while (true){
            print(disks);
            System.out.print("是否还需要回收(y/n)?");
            String t = scan.next();
            if("y".equals(t)){
                System.out.print("请输入回收的作业名:");
                recycle(disks,scan.next());
            }else{
                System.out.println("回收结束!!!");
                break;
            }
        }

    }
    public static ArrayList<Disk> init(int block_num){
        Scanner scan = new Scanner(System.in);
        ArrayList<Disk> disks = new ArrayList<>();
        System.out.println("请依次输入:");
        System.out.println("分区号\t\t\t\t大小\t\t\t\t起始");
        for (int i = 0; i < block_num; i++) {
            Disk disk = new Disk();
            disk.id = scan.nextInt();
            disk.size = scan.nextInt();
            disk.address = scan.nextInt();
            disk.station = "0";
            disks.add(disk);
        }
        return disks;
    }

    public static void print(ArrayList<Disk> disks){
        System.out.println("******************打印区块信息******************");
        System.out.println("分区号\t\t\t\t大小(KB)\t\t\t\t起始(KB)\t\t\t\t状态");
        for (int i = 0; i < disks.size(); i++) {
            System.out.println(
                    disks.get(i).id+"\t\t\t\t\t"+
                    disks.get(i).size+"\t\t\t\t\t\t"+
                    disks.get(i).address+"\t\t\t\t\t\t"+
                    disks.get(i).station);
        }
    }

    public static void job_init(int job_num,ArrayList<Disk> disks){
        Scanner scan = new Scanner(System.in);
        int j;
        boolean flag;
        System.out.println("请输入这"+job_num+"个作业的信息:");
        int[] nums = new int[job_num];
        for (int i = 0; i < job_num; i++) {
            System.out.print("请输入作业"+(i+1)+"的大小:");
            nums[i] = scan.nextInt();
        }
        for (int i = 0; i < job_num; i++) {
            j = 0;
            flag = true;
            while (j < disks.size()){
                if (disks.get(j).size >= nums[i] && "0".equals(disks.get(j).station)){
                    disks.get(j).station = "JOB"+(i+1);
                    flag = false;
                    break;
                }
                j++;
            }
            if(flag){
                System.out.println("空间不足");
            }
        }
        System.out.println("打印各作业信息:");
        System.out.println("作业名\t\t\t\t作业大小");
        for (int i = 0; i < job_num; i++) {
            System.out.println("JOB"+(i+1)+"\t\t\t\t"+nums[i]+"KB");
        }
    }
    public static boolean check(ArrayList<Disk> disks){
        for (int i = 0; i < disks.size(); i++) {
            if(!"0".equals(disks.get(i).station)){
                return true;
            }
        }
        return false;
    }

    public static boolean recycle(ArrayList<Disk> disks,String name){
        boolean flag = false;
        try {
            for (int i = 0; i < disks.size(); i++) {
                if(name.equals(disks.get(i).station)){
                    flag = true;
                    disks.get(i).station = "0";
                    System.out.println("回收成功！！！");
                    break;
                }
            }
        }catch (Exception e){
            flag =false;
            System.out.println("回收失败！！！");
        }
        return flag;
    }
}
/*
1                   12                   20
2                   32                   32
3                   64                   64
4                   128                  128
5                   100                  256
* */