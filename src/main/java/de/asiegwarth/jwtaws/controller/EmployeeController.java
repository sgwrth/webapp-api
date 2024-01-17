package de.asiegwarth.jwtaws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.asiegwarth.jwtaws.entity.Employee;
import de.asiegwarth.jwtaws.service.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService service;

    @CrossOrigin
    @PostMapping
    private ResponseEntity<Employee> save(@RequestBody Employee employee) {
        return service.save(employee);
    }

    @CrossOrigin
    @GetMapping("/{employeeId}")
    private ResponseEntity<Employee> findById(
            @PathVariable("employeeId") Long id
    ) {
        return service.findById(id);
    }

    @CrossOrigin
    @GetMapping
    private ResponseEntity<List<Employee>> findAll() {
        return service.findAll();
    }

    @CrossOrigin
    @PutMapping("/{employeeId}")
    private ResponseEntity<Employee> update(
            @PathVariable("employeeId") Long id,
            @RequestBody Employee employee
    ) {
        return service.update(id, employee);
    }

    @CrossOrigin
    @DeleteMapping("/{employeeId}")
    private ResponseEntity<Employee> delete(
            @PathVariable("employeeId") Long id
    ) {
        return service.deleteById(id);
    }

}
