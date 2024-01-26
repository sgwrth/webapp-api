package de.asiegwarth.jwtaws.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {
    
    @Id
    @GeneratedValue
    @Column(name = "employeeid")
    private Long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "createdwhen")
    private LocalDateTime createdWhen;
    @Column(name = "lasteditedby")
    private String lastEditedBy;
    @Column(name = "lasteditedwhen")
    private LocalDateTime lastEditedWhen;
    @Column(name = "salary")
    @Max(value = 100, message = "error.form.maxValue")
    private Double salary;

}
