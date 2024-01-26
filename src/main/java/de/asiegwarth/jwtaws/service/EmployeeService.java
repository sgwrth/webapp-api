package de.asiegwarth.jwtaws.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.asiegwarth.jwtaws.entity.Employee;
import de.asiegwarth.jwtaws.repository.EmployeeRespository;

public interface EmployeeService {
    
    @Service
    public class EmployeeServiceImpl implements EmployeeService {

        @Autowired
        EmployeeRespository repository;

        @Override
        public ResponseEntity<Employee> save(Employee employee) {
            employee.setCreatedWhen(LocalDateTime.now());
            employee.setLastEditedWhen(LocalDateTime.now());
            if (employee.getCreatedBy() == null) {
                employee.setCreatedBy("(backend)");
            }
            if (employee.getLastEditedBy() == null) {
                employee.setLastEditedBy("(backend)");
            }
            return ResponseEntity.ok(repository.save(employee));
        }

        @Override
        public ResponseEntity<List<Employee>> findAll() {
            return ResponseEntity.ok(repository.findAll());
        }

        @Override
        public ResponseEntity<Employee> findById(Long id) {
            return ResponseEntity.ok(repository.findById(id).get());
        }

        @Override
        public ResponseEntity<Employee> update(Long id, Employee employee) {
            Employee updatedEmployee = repository.findById(id).get();
            updatedEmployee.setFirstName(employee.getFirstName());
            updatedEmployee.setLastName(employee.getLastName());
            updatedEmployee.setLastEditedWhen(LocalDateTime.now());
            updatedEmployee.setLastEditedBy(employee.getLastEditedBy());
            return ResponseEntity.ok(repository.save(updatedEmployee));
        }

        @Override
        public ResponseEntity<Employee> deleteById(Long id) {
            Employee employee = repository.findById(id).get();
            repository.deleteById(id);
            return ResponseEntity.ok(employee);
        }
    }

    ResponseEntity<Employee> save(Employee employee);

    ResponseEntity<List<Employee>> findAll();

    ResponseEntity<Employee> findById(Long id);

    ResponseEntity<Employee> update(Long id, Employee employee);

    ResponseEntity<Employee> deleteById(Long id);

}