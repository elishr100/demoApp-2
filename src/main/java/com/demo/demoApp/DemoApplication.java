package com.demo.demoApp;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Semaphore;

@SpringBootApplication
@RestController
public class DemoApplication {

	// @GetMapping("/app2")
	// @ResponseStatus(value = HttpStatus.OK)
    // public String index() {
	// 	String message = "Hello From APP2 !!! ";
	// 	// try {
	// 	// 	InetAddress ip = InetAddress.getLocalHost();
	// 	// 	message += " From host: " + ip;
	// 	// } catch (UnknownHostException e) {
	// 	// 	e.printStackTrace();
	// 	// }

    //     return message;
    // }

	// @GetMapping("/")
	// @ResponseStatus(value = HttpStatus.OK)
    // public String index2() {
	// 	String message = "Hello From APP2 !!! ";
	// 	try {
	// 		InetAddress ip = InetAddress.getLocalHost();
	// 		message += " From host: " + ip;
	// 	} catch (UnknownHostException e) {
	// 		e.printStackTrace();
	// 	}

    //     return message;
    // }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private final Semaphore app2Semaphore;
    private final Semaphore rootSemaphore;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public DemoApplication(ThreadPoolTaskExecutor taskExecutor) {
        this.app2Semaphore = new Semaphore(2); // Allow two concurrent requests for /app2
        this.rootSemaphore = new Semaphore(2); // Allow two concurrent requests for /
        this.taskExecutor = taskExecutor;
    }

    @GetMapping("/app2")
    public ResponseEntity<String> handleApp2() {
        if (app2Semaphore.tryAcquire()) {
            taskExecutor.execute(() -> {
                try {
                    // Process the request for /app2
                    Thread.sleep(1000); // Simulating processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    app2Semaphore.release();
                }
            });

            return ResponseEntity.ok("Hello From APP2 !!!");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Endpoint /app2 is busy. Please try again later.");
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> handleRoot() {
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

            String message = "Hello From APP2 !!! ";
            try {
                InetAddress ip = InetAddress.getLocalHost();
                message += " From host: " + ip;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Endpoint / is busy. Please try again later.");
        }
    }

}
