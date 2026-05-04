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

### How to Decide Number of Threads :
1. <b>CPU-Bound Tasks</b>
   - <b>Definition</b>: Tasks that spend time waiting for external resources (e.g., network, disk operations).
     - <b>Threading</b>: More threads won’t help much, and can actually hurt performance due to context switching.
     - <b>Optimal Threads</b>: Typically, 1 thread per CPU core is enough.
     - ```java
       Threads = Number of CPU cores (Runtime.getRuntime().availableProcessors())
       ```
     - Ex: For 8 cores, use 8–10 threads.
2. <b>I/O-Bound Tasks</b>:
   - <b>Definition</b>: Tasks that use a lot of CPU power (e.g., calculations, data processing).
     - <b>Threading</b>: More threads can improve performance, as threads spend a lot of time waiting and other threads can run during that wait.
     - <b>Optimal Threads</b>: You can use many more threads than CPU cores (e.g., 50+ threads on a 4-core machine).
     - ```java
        Threads = Cores * (1 + Wait Time / Compute Time)
        ```
     - Example: For 4 cores and lots of waiting, 50+ threads might be fine.

### Executor Framework (java.util.concurrent)
- Instead of managing threads manually, Java provides the Executor Framework, which simplifies thread management and improves scalability.
- <b>ExecutorService</b>:
  - ExecutorService is an interface that provides methods to manage and control thread execution.
  - <b>Common Implementations:</b>
    - Executros.newFixedThreadPool(int nThreads)
    - Executors.newCachedThreadPool()
    - Executors.newSingleThreadExecutor()
    - Executors.newSceduledTheadPool(int corePoolSize)
  - <b>Key Methods: </b>
    - <b>submit()</b>: submits a task for execution
    - <b>shutdown()</b>: initiates an orderly shutdown.
    - <b>shutdownNow()</b>: attempts to stop all actively executing tasks.
    - <b>invokeAll()</b>: executes a collection of tasks and wait for all to finish.
    - <b>invokeAny()</b>: Executes a collection and returns the result of the fastest successful one.

<b>Example:</b>

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorTest {
    static void main()  throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = new ArrayList<>();

        for(int i=1;i<=5;i++){
            int taskId = i;
            tasks.add(() -> {
                Thread.sleep(1000);
                return "Task "+taskId+" completed by "+Thread.currentThread().getName();
            });
        }

        Future<String> singleResult = executor.submit(() -> "Single task done");

        System.out.println("Single Task Result: "+singleResult.get());

        List<Future<String>> results = executor.invokeAll(tasks);

        for(Future<String> result : results){
            System.out.println(result.get());
        }

        String fastestResult = executor.invokeAny(tasks);
        System.out.println("Fastest Result: "+fastestResult);

        executor.shutdown();
    }
}
```