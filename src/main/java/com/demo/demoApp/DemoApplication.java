package com.demo.demoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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

}
