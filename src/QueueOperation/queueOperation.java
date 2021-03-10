package QueueOperation;

public class queueOperation {

    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>(10);
        queue.push("aa");
        queue.push("cda");
        queue.push("badf");
        queue.push("eadf");
        queue.push("dsadf");
        queue.push("fasdfasdfa");
//        System.out.println(queue.search(1).toString());
//        System.out.println(queue.toString());
//        System.out.println(queue.pop());
        queue.sort();
        System.out.println(queue.toString());
    }
}
