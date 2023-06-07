package com.demo.demoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainApplication {

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
		SpringApplication.run(MainApplication.class, args);
	}

}
