package MUL_FCFS;



public class Disk implements Comparable{
    String name;// 作业名
    time inTime;// 入井时间
    time outTime;// 完成时间
    time jobTime;// 作业调度时间
    time processTime;// 进程调度时间
    int zz;// 周转时间
    float zzxs;// 带权周转时间
    int workTime;// 运行时间
    int needMemory; // 需要内存资源数
    int needTape; // 需要磁带机数
    int surplusMemory; // 分配之后此时剩余资源数
    int surplusTape;// 分配之后此时剩余磁带机数
    int finished; //标记结束返回内存资源
    int flag; //0表示作业处于后备等待队列,1处于就绪等待队列,-1表示已经结束
    float response;//响应比

    @Override
    public int compareTo(Object o) {
        if( o instanceof Disk){
            Disk t = (Disk) o;
            return (this.inTime.getHour()*60+this.inTime.getMin())-(t.inTime.getHour()*60+t.inTime.getMin());
        }
        return 0;
    }

    public int compareTo2(Object o) {
        if( o instanceof Disk){
            Disk t = (Disk) o;
            return (this.workTime - t.workTime);
        }
        return 0;
    }

    public int compareTo3(Object o) {
        if( o instanceof Disk){
            Disk t = (Disk) o;
            return (int)((this.response-t.response)*100);
        }
        return 0;
    }
}
