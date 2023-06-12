package com.demo.demoApp.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@RestController
public class DemoController {
    private final Semaphore appSemaphore;
    private final Semaphore rootSemaphore;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public DemoController(ThreadPoolTaskExecutor taskExecutor) {
        this.appSemaphore = new Semaphore(2); // Allow 1 concurrent requests for /app2
        this.rootSemaphore = new Semaphore(2); // Allow 1 concurrent requests for /
        this.taskExecutor = taskExecutor;
    }

    @GetMapping("/app3")
    public String index() {
        if (appSemaphore.tryAcquire()) {
            taskExecutor.execute(() -> {
                try {
                    // Process the request for /
                    Thread.sleep(generateRandomNumber()); // Simulating processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    appSemaphore.release();
                }
            });

            String message = "Hello From APP2 !!!";
            try {
                InetAddress ip = InetAddress.getLocalHost();
                message += " From host: " + ip;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            return message;
        } else {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return "503 Service Unavailable - Endpoint / is busy. Please try again later.";
        }
    }

    public int generateRandomNumber() {
        double randomNumber = Math.random(); // Generate a random double between 0 and 1
    
        if (randomNumber < 0.8) {
            // Generate a random number between 500 and 1000 (exclusive)
            return (int) (500 + Math.random() * 500);
        } else {
            // Generate a random number between 1000 and 1500 (inclusive)
            return (int) (1000 + Math.random() * 501);
        }
    }

    @GetMapping("/")
    public String index2() {
        if (rootSemaphore.tryAcquire()) {
            taskExecutor.execute(() -> {
                try {
                    // Process the request for /
                    Thread.sleep(1000); // Simulating processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    rootSemaphore.release();
                }
            });

            String message = "Hello From APP2 !!!";
            try {
                InetAddress ip = InetAddress.getLocalHost();
                message += " From host: " + ip;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            return message;
        } else {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return "503 Service Unavailable - Endpoint / is busy. Please try again later.";
        }
    }
}

