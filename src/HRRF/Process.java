package HRRF;

public class Process  implements Comparable{
    String name;//名字
    time arrive;//入井时间
    int zx;//执行时间
    time jobTime;//作业调度时间
    int jobWait;//作业调度等待时间
    time processTime;//进程调度时间
    int processWait;//进程调度等待时间
    time finish;// 完成时间
    int zz;// 周转时间
    float zzxs;// 带权周转系数
    int finished;// 标记作业完成
    float response;//响应比

    @Override
    public int compareTo(Object o) {
        if( o instanceof Process){
            Process t = (Process) o;
            return (int)((this.response-t.response)*100);
        }
        return 0;
    }

    public int compareTo1(Object o) {
        if( o instanceof Process){
            Process t = (Process) o;
            return (this.arrive.getHour()*60+this.arrive.getMin())-(t.arrive.getHour()*60+t.arrive.getMin());
        }
        return 0;
    }
}
