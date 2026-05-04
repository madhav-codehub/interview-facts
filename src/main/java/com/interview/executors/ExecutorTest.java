package com.interview.executors;

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
