package de.asiegwarth.jwtaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.asiegwarth.jwtaws.entity.Employee;

@Repository
public interface EmployeeRespository extends JpaRepository<Employee, Long> {

}
