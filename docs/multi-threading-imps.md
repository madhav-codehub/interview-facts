## Multithreading

### Multithreading:

- Multithreading is the ability of a CPU to execute multiple threads concurrently. In Java, it’s a fundamental part of concurrent programming.
  - A thread is a lightweight sub-process.
  - Java uses the java.lang.Thread class or implements the Runnable interface to create threads.

1. Extending <b>Thread</b> Class
2. Implementing the <b>Runnable</b> interface.

```java
class MyThread extends Thread{
    public void run(){
        System.out.println("Thread is Running: "+Thread.currentThread().getName());
    }
}

public class Main{
    static void main() {
        MyThread t1 = new MyThread();
        t1.start(); //starts a new thread
    }
}
```

```java
class MyRunnable implements Runnable{
    public void run(){
        System.out.println("Runnable thread: "+ Thread.currentThread().getName());
    }
}

public class Main{
    static void main(String[] args) {
        Thread t = new Thread(new MyRunnable());
        t.start();
    }
}
```

#### Lambda Expression:

```java
Thread thread = new Thread(() -> System.out.println("Lambda Runnable: "+Thread.currentThread().getName()));
thread.start();
```