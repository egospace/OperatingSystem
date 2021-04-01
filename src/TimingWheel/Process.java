package TimingWheel;



public class Process implements Comparable{

    private int id; //编号
    private String name; // 进程名
    private time arrive; //到达就绪队列的时间
    private int zx; //执行时间
    private time start; //每轮开始的时间
    private time first; //首次执行时间
    private int have_finished = 0; //已完成时间
    private time arr;//每轮到达时间
    private time finish;//最终完成时间
    private int zz = 0; //周转时间 = 完成时间 - 到达就绪时间
    private float zzxs = 0; // 带权周转系数 = 周转时间/执行时间
    private int flag;//是否结束

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public time getArrive() {
        return arrive;
    }

    public void setArrive(time arrive) {
        this.arrive = arrive;
    }

    public int getZx() {
        return zx;
    }

    public void setZx(int zx) {
        this.zx = zx;
    }

    public time getStart() {
        return start;
    }

    public void setStart(time start) {
        this.start = start;
    }

    public time getFirst() {
        return first;
    }

    public void setFirst(time first) {
        this.first = first;
    }

    public int getHave_finished() {
        return have_finished;
    }

    public void setHave_finished(int have_finished) {
        this.have_finished = have_finished;
    }

    public time getArr() {
        return arr;
    }

    public void setArr(time arr) {
        this.arr = arr;
    }

    public time getFinish() {
        return finish;
    }

    public void setFinish(time finish) {
        this.finish = finish;
    }

    public int getZz() {
        return zz;
    }

    public void setZz(int zz) {
        this.zz = zz;
    }

    public float getZzxs() {
        return zzxs;
    }

    public void setZzxs(float zzxs) {
        this.zzxs = zzxs;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public int compareTo(Object o) {
        if( o instanceof Process){
            Process t = (Process) o;
            return (this.arrive.getHour()*60+this.arrive.getMin())-(t.arrive.getHour()*60+t.arrive.getMin());
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", arrive=" + arrive +
                ", zx=" + zx +
                ", start=" + start +
                ", first=" + first +
                ", have_finished=" + have_finished +
                ", arr=" + arr +
                ", finish=" + finish +
                ", zz=" + zz +
                ", zzxs=" + zzxs +
                ", flag=" + flag +
                '}';
    }
}
