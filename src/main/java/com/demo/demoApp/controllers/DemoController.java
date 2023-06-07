package com.demo.demoApp.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

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
        this.appSemaphore = new Semaphore(1); // Allow 1 concurrent requests for /app2
        this.rootSemaphore = new Semaphore(1); // Allow 1 concurrent requests for /
        this.taskExecutor = taskExecutor;
    }

    @GetMapping("/app3")
    public String index() {
        if (appSemaphore.tryAcquire()) {
            taskExecutor.execute(() -> {
                try {
                    // Process the request for /app2
                    Thread.sleep(1000); // Simulating processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    appSemaphore.release();
                }
            });

            return "Hello From APP2 !!!";
        } else {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return "503 Service Unavailable - Endpoint /app2 is busy. Please try again later.";
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

