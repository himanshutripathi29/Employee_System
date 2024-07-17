package com.Springsecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Springsecurity.entity.Employee;
import com.Springsecurity.repository.EmployeeRepository;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository repository; // Based on the username i will get the id in my DB

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employee> employee = repository.findByUserName(username);
		return employee.
				map(EmployeeUserDetails::new)//Constructor reference
				.orElseThrow(() -> new UsernameNotFoundException(username+"not found in system"));
	}

}
