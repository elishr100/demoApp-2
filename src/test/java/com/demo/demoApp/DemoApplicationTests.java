package com.demo.demoApp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	
	@Test
	void testString() {
		String message = "Hello From APP2 !!! ";
		try {
			InetAddress ip = InetAddress.getLocalHost();
			message += " From host: " + ip;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		DemoApplication demoApp = new DemoApplication();
		assertEquals(message, demoApp.index());

	}

}