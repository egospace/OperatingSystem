package PageScheduling;

public class Disk {
    int page[][]; //用于存储作业调度的数组,-1表示不存在数据
    int work[]; // 输入的作业数组
    int out[]; // 存储调出页面的页号
    String name; // 作业名
    char symbol[]; // 存储缺页标志+
    int len; // 存储输入页面的长度
    int n; // 存储分配给作业的最大物理块数
}
