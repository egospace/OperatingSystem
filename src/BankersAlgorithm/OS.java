package BankersAlgorithm;




public class OS {
    private int MAX = 100;
    int N; //资源数
    int M; //进程数
    int[][] need; //P[i]尚需j类资源的数量
    int[] available; //某类资源的可用量
    int[][] max; //P[i]对资源j类的最大需求量
    int[][] allocate; //p[i]在j类资源已分配的资源量
    int[] finish;// P[i]进程是否执行结束
    int[][] request;//进程对某类资源的申请量
    int[] flag;//P[i]进程是否已经满足全部所需资源
    int[] s;//记录安全序列数组

    public void init(int N,int M){
        this.N = N;
        this.M = M;
        need = new int[MAX][MAX];
        available =new int[MAX];
        max = new int[MAX][MAX];
        allocate = new int[MAX][MAX];
        finish = new int[MAX];
        request = new int[MAX][MAX];
        flag = new int[MAX];
        s = new int[MAX];
    }
//if(!os.check(i-1)){
//
//
//    }
    public int check(int i){
        for (int k = 0; k < N; k++) {
            if(request[i][k]+allocate[i][k] <= max[i][k]){
                if(request[i][k] > available[k]){
                    System.out.println("申请资源数超过系统可用资源,您"+(char)(k+65)+"类可用资源为"+available[k]);
                    return 0;
                }
            }else{
                System.out.println("申请资源数超过最大申请资源,您"+(char)(k+65)+"类初始申请的可用资源为"+max[i][k]);
                return 0;
            }
        }

        for (int k = 0; k < N; k++) {
            available[k] = available[k] - request[i][k];
            allocate[i][k] = allocate[i][k] + request[i][k];
            need[i][k] = need[i][k] - request[i][k];
        }

        if(!security(i)){
            for (int k = 0; k < N; k++) {
                available[k] = available[k] + request[i][k];
                allocate[i][k] = allocate[i][k] - request[i][k];
                need[i][k] = need[i][k] + request[i][k];
            }
            return 1;
        }
        return 2;
    }

    private void FinishInit(){
        for (int k = 0; k < M; k++) {
            finish[k] = 0;
        }
    }

    private boolean CheckFinish(){
        for (int i = 0; i < M; i++) {
            if(finish[i] == 0 && flag[i] == 0){
                return false;
            }
        }
        return true;
    }

    private boolean CheckNeed(int i){
        for (int j = 0; j < N; j++) {
            if(need[i][j] !=0){
                return false;
            }
        }
        flag[i] = 1;
        return true;
    }

    private boolean CheckAllFlag(){
        for (int i = 0; i < M; i++)
            if(flag[i] == 0) return false;
        return true;
    }

    private boolean security(int i){
        int[] work = new int[MAX];
        boolean t;
        int q = 0;
        int w = 0;
        if(CheckNeed(i))
            for (int k = 0; k < N; k++){
                available[k] = available[k] + allocate[i][k];
            }

        for (int k = 0; k < N; k++) {
            work[k] = available[k];
        }
        FinishInit();
        while (!CheckFinish() && q < M) {
            for (int l = 0; l < M; l++) {
                if(flag[l] == 1 || finish[l] ==1) continue;
                t = true;
                for (int m = 0; m < N; m++) {
                    if(need[l][m] > work[m]){
                        t = false;
                        break;
                    }
                }
                if(t){
                    for (int m = 0; m < N; m++) {
                        work[m] = work[m] + allocate[l][m];
                        finish[l] = 1;
                    }
                    s[w++] = l;
                    break;
                }
            }
            q++;
        }
        if(CheckFinish()){
            System.out.print("申请成功！安全序列为: ");
            for (int k = 0; k < w; k++) {
                if(k == w-1)
                    System.out.print("P["+(s[k]+1)+"]\n");
                else
                    System.out.print("P["+(s[k]+1)+"]-->");
            }
            if(CheckAllFlag()) System.out.println();
            return true;
        }else{
            System.out.println("无安全序列,申请不成功!");
            System.out.println();
            return false;
        }
    }

    public void print(){
        System.out.println("进程名\t\t\t\t最大需求量\t\t\t尚需求量\t\t\t\t已分配量\t\t\t\t执行结束否");
        System.out.print("      \t\t\t\t");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print((char)(j+65)+" ");
            }
            System.out.print("\t\t\t\t");
        }
        System.out.println();

        for (int i = 0; i < M; i++) {
            System.out.print("进程p["+(i+1)+"]\t\t\t");
            for (int k = 0; k < N; k++) {
                System.out.print(max[i][k]+" ");
            }
            System.out.print("\t\t\t\t");
            for (int k = 0; k < N; k++) {
                System.out.print(need[i][k]+" ");
            }
            System.out.print("\t\t\t\t");
            for (int k = 0; k < N; k++) {
                System.out.print(allocate[i][k]+" ");
            }
            System.out.print("\t\t\t\t");
            if (flag[i] == 1){
                System.out.println("finished");
            }else{
                System.out.println("working");
            }
            System.out.println();
        }
        System.out.print("资源剩余数:\t\t");
        for (int j = 0; j < N; j++) {
            System.out.print(available[j]+" ");
        }
        System.out.println();
    }
}
