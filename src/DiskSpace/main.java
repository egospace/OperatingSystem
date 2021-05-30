package DiskSpace;

import java.util.Random;
import java.util.Scanner;

public class main {
    //1000 64 1
    //8 96
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入辅存空间的大小(单位:K)和字长(32 or 64)和块长(单位:K):");
        int size = scan.nextInt();
        int len_z = scan.nextInt();
        int len_k = scan.nextInt();
        System.out.print("请输入该辅存硬盘的磁道数(磁头数)、每磁道的扇区数:");
        int num_cd = scan.nextInt();
        int num_ct = scan.nextInt();
        int remnant = size%len_k==0?size/len_k:size/len_k+1;
        int max = remnant;
        int zihao = size%(len_k*len_z)==0?size/(len_k*len_z):size/(len_k*len_z)+1;
        int[][] memory = new int[len_z][zihao];
        Disk disk = new Disk();
        disk.next = new Disk();
        Disk temp = disk.next;
        remnant = init(memory,remnant,len_z,zihao,max);
        System.out.println("*********************辅存初始位示图如下*********************");
        print(memory,remnant,len_z,zihao,max);
        boolean flag = true;
        while(flag){
            switch (menu()){
                case 1:
                    System.out.println("*********************打印内存空间情况*********************");
                    print(memory,remnant,len_z,zihao,max);
                    remnant = allocation(memory,remnant,len_z,zihao,temp,len_k,num_cd,num_ct);
                    System.out.println("*********************打印内存空间情况*********************");
                    print(memory,remnant,len_z,zihao,max);
                    temp.next = new Disk();
                    temp = temp.next;
                    break;
                case 2:
                    System.out.println("*********************打印内存空间情况*********************");
                    print(memory,remnant,len_z,zihao,max);
                    remnant = free(memory,remnant,len_z,zihao,disk);
                    System.out.println("*********************打印内存空间情况*********************");
                    print(memory,remnant,len_z,zihao,max);
                    break;
                default:
                    flag = false;
                    break;
            }
        }
    }

    public static int init(int[][] memory,int remnant,int len_z,int zihao,int max){
        Random r = new Random();
        int a;
        int b;
        while (remnant > 500){
            a = r.nextInt(len_z);
            b = r.nextInt(zihao);
            if(memory[a][b] == 0 && a+b*len_z<max){
                memory[a][b] = 1;
                remnant--;
            }
        }
        return remnant;
    }

    public static int menu(){
        System.out.println("*********************辅存管理*********************");
        System.out.println("          *     1.空间分配      *");
        System.out.println("          *     2.空间去配      *");
        System.out.println("          *     0.退出          *");
        System.out.print("请输入选项:");
        return new Scanner(System.in).nextInt();
    }

    public static int allocation(int[][] memory,int remnant,int len_z,int zihao,Disk disk,int len_k,int num_cd,int num_ct){
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入申请空间的作业名字和需要分配辅存空间的大小:");
        disk.name = scan.next();
        disk.size = scan.nextInt();
        int len = disk.size%len_k==0?disk.size/len_k:disk.size/len_k+1;
        disk.a = new int[len];
        disk.zihao = new int[len];
        disk.weihao = new int[len];
        disk.zhu = new int[len];
        disk.citou = new int[len];
        disk.shanqu = new int[len];
        int t = 0;
        for (int i = 0; i < zihao; i++) {
            if(t >= len) break;
            for (int j = 0; j < len_z; j++) {
                if(t >= len) break;
                if(memory[j][i] == 0){
                    memory[j][i] = 1;
                    disk.a[t] = i * len_z + j;
                    disk.zihao[t] = i;
                    disk.weihao[t] = j;
                    disk.zhu[t] = disk.a[t]/(num_cd*num_ct);
                    disk.citou[t] = (disk.a[t]%(num_cd*num_ct))/num_cd;
                    disk.shanqu[t] = (disk.a[t]%(num_cd*num_ct))%num_cd;
                    t++;
                    remnant--;
                }
            }
        }
        System.out.println("*********************打印"+disk.name+"作业在辅存中的信息*********************");
        System.out.println("记录\t块号\t柱面号\t磁头号\t扇区号");
        for (int i = 0; i < disk.a.length; i++) {
            System.out.println((i+1)+"\t\t"+disk.a[i]+"\t\t"+disk.zhu[i]+"\t\t"+disk.citou[i]+"\t\t"+disk.shanqu[i]);
        }
        System.out.println("内存分配成功！");
        return remnant;
    }

    public static int free(int[][] memory,int remnant,int len_z,int zihao,Disk disk){
        Disk disk1 = disk.next;
        Scanner scan = new Scanner(System.in);
        System.out.print("当前分配的作业:");
        while (disk1 != null){
            if (disk1.next.name == null){
                System.out.print(disk1.name);
                break;
            }else{
                System.out.print(disk1.name+"->");
            }
            disk1 = disk1.next;
        }
        System.out.println();
        System.out.print("请输入你当前要回收的作业名:");
        String name = scan.next();
        Disk disk2 = disk;
        while (disk2.next!= null){
            if(disk2.next.name.equals(name)) {
                int len = disk2.next.a.length;
                int t = 0;
                while (t < len){
                    memory[disk2.next.weihao[t]][disk.next.zihao[t]] = 0;
                    t++;
                    remnant++;
                }
                System.out.println("*********************打印"+disk2.next.name+"作业在辅存中的信息*********************");
                System.out.println("记录\t块号\t柱面号\t磁头号\t扇区号");
                System.out.println("该作业无存储信息！回收成功！");
                disk2.next = disk2.next.next;
                break;
            }
            disk2 = disk2.next;
        }
        return remnant;
    }

    public static void print(int[][] memory,int remnant,int len_z,int zihao,int max){
        System.out.print(" \t");
        for (int i = 0; i < len_z; i++) {
            System.out.print(i+"\t");
        }
        System.out.println();
        for (int i = 0; i < zihao; i++) {
            System.out.print(i+"\t");
            for (int j = 0; j < len_z; j++) {
                if (j+i*len_z>=max) break;
                System.out.print(memory[j][i]+"\t");
            }
            System.out.println();
        }
        System.out.println("辅存剩余空块数:"+remnant);
    }
}
