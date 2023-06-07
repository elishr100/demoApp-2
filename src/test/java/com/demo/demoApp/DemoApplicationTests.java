package com.demo.demoApp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.demo.demoApp.controllers.DemoController;



@SpringBootTest
class DemoApplicationTests {

	@MockBean
    private ThreadPoolTaskExecutor taskExecutor;
	
	@Test
	void testString() {
		String message = "Hello From APP2 !!!";

		// try {
		// 	InetAddress ip = InetAddress.getLocalHost();
		// 	message += " From host: " + ip;
		// } catch (UnknownHostException e) {
		// 	e.printStackTrace();
		// }

		DemoController demoApp = new DemoController(taskExecutor);
		assertEquals(message, demoApp.handleApp2());

	}

}
