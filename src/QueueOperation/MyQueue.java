package QueueOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class MyQueue<T> {

    private int maxSize;
    private int front;
    private int rear;
    private T[] queue;


    public MyQueue(int maxSize) {
        if(maxSize < 1){
            this.maxSize = 0;
        }else{
            this.maxSize = maxSize;
        }
        this.front = 0;
        this.rear = 0;
        this.queue = (T[]) new Object[this.maxSize];
    }

    public boolean isNull(){
        return rear == front;
    }

    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }

    public boolean push(T data){
        if(isFull()){
            return false;
        }else{
            queue[rear] = data;
            rear = (rear + 1) % maxSize;
            return true;
        }
    }

    public T pop(){
        if(isNull()){
            return null;
        }else{
            T data = queue[front];
            front = (front + 1) % maxSize;
            return data;
        }
    }

    public boolean modify(int loc, T data){
        if(loc % maxSize < rear && loc % maxSize >= front){
            queue[loc] = data;
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Integer> search(T data){
        ArrayList<Integer> list = new ArrayList<>();
        int flag = front;
        if("class java.lang.String".equals(data.getClass().toString())){
            while((rear + 1) % maxSize != flag){
                if(data.equals(queue[flag])){
                    list.add(flag);
                }
                flag = (flag + 1)%maxSize;
            }
        }else{
            while((rear + 1) % maxSize != flag){
                if(queue[flag] == data){
                    list.add(flag);
                }
                flag = (flag + 1)%maxSize;
            }
        }
        return list;
    }

    public void sort(){
        T data = queue[front];
        ArrayList<T> list = new ArrayList<>();
        int flag = front;
        while ((rear + 1) % maxSize != flag) {
            list.add(queue[flag]);
            flag = (flag + 1) % maxSize;
        }
        int size = list.size();
        for (int i=0; i<size-1; i++)
            for (int j = i; j>0 && ((Comparable) list.get(j - 1)).compareTo(list.get(j))>0; j--)
                swap(list, j, j-1);
        flag = front;
        int j = 0;
        while ((rear + 1) % maxSize != flag && j < size) {
            queue[flag] = list.get(j++);
            flag = (flag + 1) % maxSize;
        }
    }
    private  void swap(ArrayList<T> x, int a, int b) {
        T t =  x.get(a);
        x.set(a, x.get(b));
        x.set(b, t);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public T[] getQueue() {
        return queue;
    }

    public void setQueue(T[] queue) {
        this.queue = queue;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }

    @Override
    public String toString() {
        return "MyQueue{" +
                "maxSize=" + maxSize +
                ", front=" + front +
                ", rear=" + rear +
                ", queue=" + Arrays.toString(queue) +
                '}';
    }
}