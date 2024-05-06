package de.asiegwarth.jwtaws.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentType {
    
    @Id
    @GeneratedValue
    @Column(name = "doktypid")
    private Long docTypeId;
    @Column(name = "doktypname")
    private String docTypeName;

}
