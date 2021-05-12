package SectionsOfStorage;


import java.util.ArrayList;
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
        ArrayList<SegmentTable> tables = new ArrayList<>();
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
                            System.out.print("请输入分成几段:");
                            int piece = scan.nextInt();
                            int[][] table = TFA(allocatedPartition,freePartition,jobTFA,sizeTFA,piece);
                            SegmentTable segmentTable = new SegmentTable();
                            segmentTable.name = jobTFA;
                            segmentTable.table = table;
                            tables.add(segmentTable);
                            printSe(segmentTable);
                            temp = 1;
                            break;
                        case 2:
                            print(allocatedPartition,freePartition);
                            System.out.print("请输入作业名:");
                            String jobOAM = scan.next();
                            System.out.print("请输入"+jobOAM+"需要分配的主存大小(单位: KB):");
                            int sizeOAM = scan.nextInt();
                            System.out.print("请输入分成几段:");
                            int pieceOAM  = scan.nextInt();
                            int[][] tableOAM  = OAM(allocatedPartition,freePartition,jobOAM,sizeOAM, pieceOAM);
                            SegmentTable segmentTableOAM = new SegmentTable();
                            segmentTableOAM.name = jobOAM;
                            segmentTableOAM.table = tableOAM;
                            tables.add(segmentTableOAM );
                            printSe(segmentTableOAM );
                            temp = 2;
                            break;
                        case 3:
                            print(allocatedPartition,freePartition);
                            System.out.print("请输入作业名:");
                            String jobWAA = scan.next();
                            System.out.print("请输入"+jobWAA+"需要分配的主存大小(单位: KB):");
                            int sizeWAA = scan.nextInt();
                            System.out.print("请输入分成几段:");
                            int pieceWAA = scan.nextInt();
                            int[][] tableWAA = WAA(allocatedPartition,freePartition,jobWAA,sizeWAA,pieceWAA);
                            SegmentTable segmentTableWAA = new SegmentTable();
                            segmentTableWAA.name = jobWAA;
                            segmentTableWAA.table = tableWAA;
                            tables.add(segmentTableWAA);
                            printSe(segmentTableWAA);
                            temp = 3;
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    print(allocatedPartition,freePartition);
                    System.out.print("请输入你要回收的作业名:");
                    String job = scan.next();
                    recycleTFA(allocatedPartition,freePartition,job,tables);
                    print(allocatedPartition,freePartition);
                    break;
                default:
                    tag = false;
                    break;
            }
        }
    }

    public static  int[][] TFA(Disk allocatedPartition, Disk freePartition, String job, int size, int piece){
        Scanner scan = new Scanner(System.in);
        int[][] tables = new int[piece][3];
        Disk alloc,free;
        int tempSize = size;
        for (int i = 0; i < piece; i++) {
            System.out.print("剩余"+tempSize+"K的内存，请输入第"+(i+1)+"段的大小(单位:KB):");
            int temp = scan.nextInt();
            tempSize -= temp;
            alloc = allocatedPartition;
            free = freePartition.next;
            try{
                do {
                    if (free.size >= temp){
                        try{
                            do {
                                if (alloc.next != null){
                                    if (alloc.next.address > free.address){
                                        Disk disk = new Disk();
                                        disk.flag = job+"("+i+")";
                                        disk.size = temp;
                                        disk.address = free.address;
                                        disk.next=alloc.next;
                                        alloc.next = disk;
                                        tables[i][0] = i;
                                        tables[i][1] = temp;
                                        tables[i][2] = free.address;
                                        break;
                                    }
                                }else{
                                    Disk disk = new Disk();
                                    disk.address = free.address;
                                    disk.size = temp;
                                    disk.flag = job+"("+i+")";
                                    disk.next = null;
                                    alloc.next = disk;
                                    tables[i][0] = i;
                                    tables[i][1] = temp;
                                    tables[i][2] = free.address;
                                    break;
                                }
                                alloc = alloc.next;
                            }while (true);
                        }catch (Exception ignored){}
                        free.size -= temp;
                        free.address += temp;
                        break;
                    }
                    free = free.next;
                }while (free != null);
            }catch (Exception ignored){}
            print(allocatedPartition,freePartition);
            System.out.println("分配成功!");
        }
        return tables;
    }

    public static int[][] OAM(Disk allocatedPartition, Disk freePartition, String job, int size, int piece){
        Scanner scan = new Scanner(System.in);
        int[][] tables = new int[piece][3];
        Disk alloc,free;
        int tempSize = size;
        for (int i = 0; i < piece; i++) {
            System.out.print("剩余"+tempSize+"K的内存，请输入第"+(i+1)+"段的大小(单位:KB):");
            int temp = scan.nextInt();
            tempSize -= temp;
            alloc = allocatedPartition;
            free = freePartition.next;
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
                                        disk.flag = job+"("+i+")";
                                        disk.size = temp;
                                        disk.address = free.address;
                                        disk.next=alloc.next;
                                        alloc.next = disk;
                                        tables[i][0] = i;
                                        tables[i][1] = temp;
                                        tables[i][2] = free.address;
                                        break;
                                    }
                                }else{
                                    Disk disk = new Disk();
                                    disk.address = free.address;
                                    disk.size = temp;
                                    disk.flag = job+"("+i+")";
                                    disk.next = null;
                                    alloc.next = disk;
                                    tables[i][0] = i;
                                    tables[i][1] = temp;
                                    tables[i][2] = free.address;
                                    break;
                                }
                                alloc = alloc.next;
                            }while (true);
                        }catch (Exception ignored){}
                        free.size -= temp;
                        free.address += temp;
                        break;
                    }
                    free = free.next;
                }while (free != null);
            }catch (Exception ignored){}
            print(allocatedPartition,freePartition);
            System.out.println("分配成功!");
        }
        return tables;
    }

    public static int[][] WAA(Disk allocatedPartition, Disk freePartition, String job, int size, int piece){
        Scanner scan = new Scanner(System.in);
        int[][] tables = new int[piece][3];
        Disk alloc,free;
        int tempSize = size;
        for (int i = 0; i < piece; i++) {
            System.out.print("剩余"+tempSize+"K的内存，请输入第"+(i+1)+"段的大小(单位:KB):");
            int temp = scan.nextInt();
            tempSize -= temp;
            alloc = allocatedPartition;
            free = freePartition.next;
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
                                        disk.flag = job+"("+i+")";
                                        disk.size = temp;
                                        disk.address = free.address;
                                        disk.next=alloc.next;
                                        alloc.next = disk;
                                        tables[i][0] = i;
                                        tables[i][1] = temp;
                                        tables[i][2] = free.address;
                                        break;
                                    }
                                }else{
                                    Disk disk = new Disk();
                                    disk.address = free.address;
                                    disk.size = temp;
                                    disk.flag = job+"("+i+")";
                                    disk.next = null;
                                    alloc.next = disk;
                                    tables[i][0] = i;
                                    tables[i][1] = temp;
                                    tables[i][2] = free.address;
                                    break;
                                }
                                alloc = alloc.next;
                            }while (true);
                        }catch (Exception ignored){}
                        free.size -= temp;
                        free.address += temp;
                        break;
                    }
                    free = free.next;
                }while (free != null);
            }catch (Exception ignored){}
            print(allocatedPartition,freePartition);
            System.out.println("分配成功!");
        }
        return tables;
    }

    public static void recycleTFA(Disk allocatedPartition, Disk freePartition, String job, ArrayList<SegmentTable> tables){
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).name.equals(job)){
                Disk alloc;
                Disk free;
                for (int j = 0; j < tables.get(i).table.length; j++) {
                    alloc = allocatedPartition;
                    free = freePartition;
                    try{
                        do {
                            if (tables.get(i).table[j][2] == alloc.next.address){
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
                                System.out.println("回收"+job+"的段"+job+"("+j+")"+"回收成功!");
                                break;
                            }
                            alloc = alloc.next;
                        }while (alloc.next != null);
                    }catch (Exception ignored){}
                }
                break;
            }
        }
    }

    public static void printSe(SegmentTable table){
        System.out.println("**********************打印"+table.name+"段表**********************");
        System.out.println("段号\t\t\t\t段长(KB)\t\t\t\t基址(KB)");
        for (int i = 0; i < table.table.length; i++) {
            System.out.println(table.table[i][0]+"\t\t\t\t\t"+table.table[i][1]+"\t\t\t\t\t\t"+table.table[i][2]);
        }
    }

    public static void print(Disk allocatedPartition, Disk freePartition){
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
        System.out.println("****************段式存储管理****************");
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
