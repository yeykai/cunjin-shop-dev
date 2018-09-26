package com.yeykai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="hello测试接口",tags= {"hello Controller"})
@RequestMapping("/yeykai")
public class HelloWorldController {
	
	@GetMapping("/hello")
	@ApiOperation(value="hello",notes="hello 测试")
	public String Hello() {
		return "Hello Spring Boot~";
	}
	
}
