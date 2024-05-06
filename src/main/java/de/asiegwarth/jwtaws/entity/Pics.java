package de.asiegwarth.jwtaws.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "v_pics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pics {
    
    @Id
    @Column(name = "picid")
    private Long picid;
    @Column(name = "email")
    private String email;
    @Column(name = "path")
    private String path;
    @Column(name = "filename")
    private String fileName;

}
