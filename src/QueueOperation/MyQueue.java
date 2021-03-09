package QueueOperation;

import java.util.ArrayList;
import java.util.Arrays;

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

    public T[] sort(){
        T data = queue[front];
        if("class java.lang.String".equals(data.getClass().toString())){
//            while((rear + 1) % maxSize != flag){
//                if(data.equals(queue[flag])){
//                    list.add(flag);
//                }
//                flag = (flag + 1)%maxSize;
//            }
        }else{
//            while((rear + 1) % maxSize != flag){
//                if(queue[flag] == data){
//                    list.add(flag);
//                }
//                flag = (flag + 1)%maxSize;
//            }
        }
        return queue;
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
