package com.jetbrains.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {}

	@Test
	@DisplayName("Check if health check returns ok")
	public void testHealthCheckIsOk() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/health-check"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, World!"));
	}

	@Test
	@DisplayName("Check if health check is not found (404) [ALWAYS FAILED]")
	public void testHealthCheckIsNotOk() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/health-check"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Check if list returns 4 items")
	public void testListContains4Items() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/list"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"));
	}
}
