package com.Springsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Springsecurity.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Optional<Employee> findByUserName(String username);
	
	

}
