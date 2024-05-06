package de.asiegwarth.jwtaws.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentZuordnungen {
    
    @Column(name = "email")
    private String email;
    @Column(name = "dokument")
    private Long documentId;

}
