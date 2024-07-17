package com.Springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityDemoController {
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to new Project in Security";
	}
	
	@GetMapping("/home")
	public String greeting() {
		return "Hope you are doing good";
	}
	@GetMapping("/nonsecure")
	public String nonsecureweb() {
		return "This web site is not secure";
	}

}
