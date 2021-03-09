package QueueOperation;

public class queueOperation {

    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>(10);
        queue.push("1");
        queue.push("2");
        System.out.println(queue.search("1").toString());
        System.out.println(queue.toString());
        System.out.println(queue.pop());
        System.out.println(queue.toString());
    }
}
