package VariablePartition;

public class Disk {
    int size;// 分区大小
    int address;// 分区始址
    String flag;// 分区状态,空闲或者占用作业名
    Disk next;
}
