package no.hvl.dat103;

import java.util.LinkedList;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

        Controller controller = new Controller();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    controller.produce();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    controller.consume();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static class Controller {
        int BUFFER_CAPACITY = 2;
        LinkedList<Integer> BUFFER = new LinkedList<>();

        public void produce() throws InterruptedException {
            int value = 0;
            while (true)
                synchronized (this) {
                    while (BUFFER.size() == BUFFER_CAPACITY)
                        wait();

                    System.out.println("Producer produced-" + value);

                    BUFFER.add(value++);
                    notify();
                    Thread.sleep(1000);
                }
        }

        public void consume() throws InterruptedException {
            while (true)
                synchronized (this) {
                    while (BUFFER.size() == 0)
                        wait();

                    int val = BUFFER.removeFirst();

                    System.out.println("Consumer consumed-" + val);
                    notify();
                    Thread.sleep(1000);
                }
        }
    }

}
