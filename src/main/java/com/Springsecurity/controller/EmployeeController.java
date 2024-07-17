package com.Springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Springsecurity.dto.AuthRequest;
import com.Springsecurity.entity.Employee;
import com.Springsecurity.service.EmployeeService;
import com.Springsecurity.service.JwtService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Hello Himanshu Learn Spring Security  !! Your offical credential already has been shared over emial";
	}

	@PostMapping("/create")
	// @PreAuthorize("hasAuthority('ROLE_HR')")
	public Employee onboardNewEmployee(@RequestBody Employee employee) {
		return service.createNewEmployee(employee);

	}

	@PostMapping("/authenticate")
	public String authenticate(@RequestBody AuthRequest authRequest) {

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		if (authenticate.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUserName());
		} else {
			throw new UsernameNotFoundException("Auhentication filed");
		}

	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_HR') or hasAuthority('ROLE_MANAGER') ")
	public List<Employee> getAll() {
		return service.getEmployees();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
	public Employee getEmployeeById(@PathVariable Integer id) {
		return service.getEmployee(id);

	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ROLE_HR')")
	// @PreAuthorize(AUTHORITY_ROLE_HR): -> we can also use this type
	public Employee updateRoles(@RequestBody Employee employee) {
		return service.changeRoleofEmployee(employee);
	}

}
