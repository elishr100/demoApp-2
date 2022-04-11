package com.demo.demoApp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	
	@Test
	void testString() {
		DemoApplication demoApp = new DemoApplication();
		assertEquals("Hello from Demo application 2", demoApp.index());

	}

}