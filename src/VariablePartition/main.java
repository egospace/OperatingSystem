package VariablePartition;


import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入内存大小为:");
        int max = scan.nextInt();
        System.out.print("请输入起始地址大小为:");
        int startAddress = scan.nextInt();
        Disk freePartition = new Disk();
        Disk freeDisk = new Disk();
        freeDisk.address =startAddress;
        freeDisk.size = max;
        freeDisk.flag = "空闲";
        freePartition.next = freeDisk;
        Disk allocatedPartition = new Disk();
        boolean tag = true;
        int temp = 0;
        while (tag){
            switch (menuPartition()){
                case 1:
                    switch (menuAG()){
                        case 1:
                            print(allocatedPartition,freePartition);
                            System.out.print("请输入作业名:");
                            String jobTFA = scan.next();
                            System.out.print("请输入"+jobTFA+"需要分配的主存大小(单位: KB):");
                            int sizeTFA = scan.nextInt();
                            TFA(allocatedPartition,freePartition,jobTFA,sizeTFA);
                            print(allocatedPartition,freePartition);
                            temp = 1;
                            break;
                        case 2:
                            print(allocatedPartition,freePartition);
                            System.out.print("请输入作业名:");
                            String jobOAM = scan.next();
                            System.out.print("请输入"+jobOAM+"需要分配的主存大小(单位: KB):");
                            int sizeOAM = scan.nextInt();
                            OAM(allocatedPartition,freePartition,jobOAM,sizeOAM);
                            print(allocatedPartition,freePartition);
                            temp = 2;
                            break;
                        case 3:
                            print(allocatedPartition,freePartition);
                            System.out.print("请输入作业名:");
                            String jobWAA = scan.next();
                            System.out.print("请输入"+jobWAA+"需要分配的主存大小(单位: KB):");
                            int sizeWAA = scan.nextInt();
                            WAA(allocatedPartition,freePartition,jobWAA,sizeWAA);
                            print(allocatedPartition,freePartition);
                            temp = 3;
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
//                    switch (temp){
//                        case 2:
//                            print(allocatedPartition,freePartition);
//                            System.out.print("请输入你要回收的作业名:");
//                            String jobOMA = scan.next();
//                            recycleOMA(allocatedPartition,freePartition,jobOMA);
//                            print(allocatedPartition,freePartition);
//                            break;
//                        case 3:
//                            print(allocatedPartition,freePartition);
//                            System.out.print("请输入你要回收的作业名:");
//                            String jobWAA = scan.next();
//                            recycleWAA(allocatedPartition,freePartition,jobWAA);
//                            print(allocatedPartition,freePartition);
//                            break;
//                        default:
//                            print(allocatedPartition,freePartition);
//                            System.out.print("请输入你要回收的作业名:");
//                            String job = scan.next();
//                            recycleTFA(allocatedPartition,freePartition,job);
//                            print(allocatedPartition,freePartition);
//                            break;
//                    }
                    print(allocatedPartition,freePartition);
                    System.out.print("请输入你要回收的作业名:");
                    String job = scan.next();
                    recycleTFA(allocatedPartition,freePartition,job);
                    print(allocatedPartition,freePartition);
                    break;
                default:
                    tag = false;
                    break;
            }
        }
    }

    public static  void TFA(Disk allocatedPartition,Disk freePartition,String job, int size){
        Disk alloc = allocatedPartition;
        Disk free = freePartition.next;
        try{
            do {
                if (free.size >= size){
                    try{
                        do {
                            if (alloc.next != null){
                                if (alloc.next.address > free.address){
                                    Disk disk = new Disk();
                                    disk.flag = job;
                                    disk.size = size;
                                    disk.address = free.address;
                                    disk.next=alloc.next;
                                    alloc.next = disk;
                                    break;
                                }
                            }else{
                                Disk disk = new Disk();
                                disk.address = free.address;
                                disk.size = size;
                                disk.flag = job;
                                disk.next = null;
                                alloc.next = disk;
                                break;
                            }
                            alloc = alloc.next;
                        }while (true);
                    }catch (Exception ignored){}
                    free.size -= size;
                    free.address += size;
                    System.out.println("分配成功!");
                    break;
                }
                free = free.next;
            }while (free != null);
        }catch (Exception ignored){}
    }

    public static void OAM(Disk allocatedPartition,Disk freePartition,String job, int size){
        Disk alloc = allocatedPartition;
        Disk free = freePartition.next;
        int min = 999999999;
        try{
            do {
                if (free.size >= size){
                    if (min > free.size){
                        min = free.size;
                    }
                }
                free = free.next;
            }while (free != null);
            free = freePartition.next;
            do {
                if (free.size == min){
                    try{
                        do {
                            if (alloc.next != null){
                                if (alloc.next.address > free.address){
                                    Disk disk = new Disk();
                                    disk.flag = job;
                                    disk.size = size;
                                    disk.address = free.address;
                                    disk.next=alloc.next;
                                    alloc.next = disk;
                                    break;
                                }
                            }else{
                                Disk disk = new Disk();
                                disk.address = free.address;
                                disk.size = size;
                                disk.flag = job;
                                disk.next = null;
                                alloc.next = disk;
                                break;
                            }
                            alloc = alloc.next;
                        }while (true);
                    }catch (Exception ignored){}
                    free.size -= size;
                    free.address += size;
                    System.out.println("分配成功!");
                    break;
                }
                free = free.next;
            }while (free != null);
        }catch (Exception ignored){}
    }

    public static void WAA(Disk allocatedPartition,Disk freePartition,String job, int size){
        Disk alloc = allocatedPartition;
        Disk free = freePartition.next;
        int max = -1;
        try{
            do {
                if (free.size >= size){
                    if (max < free.size){
                        max = free.size;
                    }
                }
                free = free.next;
            }while (free != null);
            free = freePartition.next;
            do {
                if (free.size == max){
                    try{
                        do {
                            if (alloc.next != null){
                                if (alloc.next.address > free.address){
                                    Disk disk = new Disk();
                                    disk.flag = job;
                                    disk.size = size;
                                    disk.address = free.address;
                                    disk.next=alloc.next;
                                    alloc.next = disk;
                                    break;
                                }
                            }else{
                                Disk disk = new Disk();
                                disk.address = free.address;
                                disk.size = size;
                                disk.flag = job;
                                disk.next = null;
                                alloc.next = disk;
                                break;
                            }
                            alloc = alloc.next;
                        }while (true);
                    }catch (Exception ignored){}
                    free.size -= size;
                    free.address += size;
                    System.out.println("分配成功!");
                    break;
                }
                free = free.next;
            }while (free != null);
        }catch (Exception ignored){}
    }

    public static void recycleTFA(Disk allocatedPartition,Disk freePartition,String job){
        Disk alloc = allocatedPartition;
        Disk free = freePartition;
        try{
            do {
                if (job.equals(alloc.next.flag)){
                    Disk f = free;
                    do {
                        if (free.next != null){
                            if (free.next.address > alloc.next.address){
                                Disk disk = new Disk();
                                disk.address = alloc.next.address;
                                disk.size = alloc.next.size;
                                disk.flag = "空闲";
                                disk.next = free.next;
                                free.next = disk;
                                if (disk.address+disk.size == disk.next.address){
                                    disk.size += disk.next.size;
                                    disk.next = disk.next.next;
                                }
                                if(free.address+free.size == disk.address){
                                    free.size += disk.size;
                                    free.next = disk.next;
                                }
                                break;
                            }
                        }else{
                            Disk disk = new Disk();
                            disk.address = alloc.next.address;
                            disk.size = alloc.next.size;
                            disk.flag = "空闲";
                            disk.next = null;
                            f.next = disk;
                            if(f.address+f.size == f.address){
                                f.size += disk.size;
                                f.next = null;
                            }
                            break;
                        }
                        f = free;
                        free = free.next;
                    }while (true);
                    alloc.next = alloc.next.next;
                    System.out.println("回收成功!");
                    break;
                }
                alloc = alloc.next;
            }while (alloc.next != null);
        }catch (Exception ignored){}
    }

//    public static void recycleOMA(Disk allocatedPartition,Disk freePartition,String job){
//        Disk alloc = allocatedPartition;
//        Disk free = freePartition;
//        try{
//            do {
//                if (job.equals(alloc.next.flag)){
//                    Disk f = free;
//                    do {
//                        if (free.next != null){
//                            if (free.next.address > alloc.next.address){
//                                Disk disk = new Disk();
//                                disk.address = alloc.next.address;
//                                disk.size = alloc.next.size;
//                                disk.flag = "空闲";
//                                disk.next = free.next;
//                                free.next = disk;
//                                if (disk.address+disk.size == disk.next.address){
//                                    disk.size += disk.next.size;
//                                    disk.next = disk.next.next;
//                                }
//                                if(free.address+free.size == disk.address){
//                                    free.size += disk.size;
//                                    free.next = disk.next;
//                                }
//                                break;
//                            }
//                        }else{
//                            Disk disk = new Disk();
//                            disk.address = alloc.next.address;
//                            disk.size = alloc.next.size;
//                            disk.flag = "空闲";
//                            disk.next = null;
//                            f.next = disk;
//                            if(f.address+f.size == f.address){
//                                f.size += disk.size;
//                                f.next = null;
//                            }
//                            break;
//                        }
//                        f = free;
//                        free = free.next;
//                    }while (true);
//                    alloc.next = alloc.next.next;
//                    System.out.println("回收成功!");
//                    break;
//                }
//                alloc = alloc.next;
//            }while (alloc.next != null);
//        }catch (Exception ignored){}
//    }
//
//    public static void recycleWAA(Disk allocatedPartition,Disk freePartition,String job){
//        Disk alloc = allocatedPartition;
//        Disk free = freePartition;
//        try{
//            do {
//                if (job.equals(alloc.next.flag)){
//                    Disk f = free;
//                    do {
//                        if (free.next != null){
//                            if (free.next.address > alloc.next.address){
//                                Disk disk = new Disk();
//                                disk.address = alloc.next.address;
//                                disk.size = alloc.next.size;
//                                disk.flag = "空闲";
//                                disk.next = free.next;
//                                free.next = disk;
//                                if (disk.address+disk.size == disk.next.address){
//                                    disk.size += disk.next.size;
//                                    disk.next = disk.next.next;
//                                }
//                                if(free.address+free.size == disk.address){
//                                    free.size += disk.size;
//                                    free.next = disk.next;
//                                }
//                                break;
//                            }
//                        }else{
//                            Disk disk = new Disk();
//                            disk.address = alloc.next.address;
//                            disk.size = alloc.next.size;
//                            disk.flag = "空闲";
//                            disk.next = null;
//                            f.next = disk;
//                            if(f.address+f.size == f.address){
//                                f.size += disk.size;
//                                f.next = null;
//                            }
//                            break;
//                        }
//                        f = free;
//                        free = free.next;
//                    }while (true);
//                    alloc.next = alloc.next.next;
//                    System.out.println("回收成功!");
//                    break;
//                }
//                alloc = alloc.next;
//            }while (alloc.next != null);
//        }catch (Exception ignored){}
//    }

    public static void print(Disk allocatedPartition,Disk freePartition){
        Disk alloc = allocatedPartition.next;
        Disk free = freePartition.next;
        int len = 1;
        System.out.println("**********************主存分配情况**********************");
        System.out.println("已分配:");
        System.out.println("分区号\t\t\t\t大小(KB)\t\t\t\t起始(KB)\t\t\t\t状态");
        try{
            do {
                System.out.println(len+"\t\t\t\t\t"+alloc.size +
                        "\t\t\t\t\t\t"+alloc.address+
                        "\t\t\t\t\t\t"+alloc.flag);
                len++;
                alloc = alloc.next;
            }while (alloc != null);
        }catch (Exception ignored){}
        System.out.println("未分配:");
        System.out.println("分区号\t\t\t\t大小(KB)\t\t\t\t起始(KB)\t\t\t\t状态");
        try{
            do {
                System.out.println(len+"\t\t\t\t\t"+free.size +
                        "\t\t\t\t\t\t"+free.address+
                        "\t\t\t\t\t\t"+free.flag);
                len++;
                free = free.next;
            }while (free != null);
        }catch (Exception ignored){}
    }

    public static int menuPartition(){
        System.out.println("****************可变分区管理****************");
        System.out.println("          *     1.内存分配       *          ");
        System.out.println("          *     2.内存去配       *          ");
        System.out.println("          *     0.退出           *          ");
        System.out.print("请输入选项:");
        return new Scanner(System.in).nextInt();
    }

    public static int menuAG(){
        System.out.println("****************分配算法****************");
        System.out.println("          *     1.最先分配法       *          ");
        System.out.println("          *     2.最优分配法       *          ");
        System.out.println("          *     3.最坏分配法       *          ");
        System.out.print("请输入选项:");
        return new Scanner(System.in).nextInt();
    }

}
