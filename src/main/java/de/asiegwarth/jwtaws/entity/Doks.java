package de.asiegwarth.jwtaws.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "v_doks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Doks.class)
public class Doks {

    @Id
    @Column(name = "userid")
    private Long userId;
    @Column(name = "email")
    private String email;
    @Id
    @Column(name = "dateiname")
    private String fileName;
    @Column(name = "dateipfad")
    private String filePath;
    
}
