package DiskSpace;

public class Disk {
    int size;// 需要分配空间的大小（记录总数）
    int[] a;// 块号
    int[] zihao;// 字号
    int[] weihao;// 位号
    int[] zhu;// 柱面号
    int[] citou;// 磁头号
    int[] shanqu;// 扇区号
    String name;// 作业名
    Disk next;
}
