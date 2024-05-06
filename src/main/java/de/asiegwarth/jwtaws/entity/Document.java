package de.asiegwarth.jwtaws.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "dokument")
public class Document implements Serializable { 
    
    @Id
    @GeneratedValue
    @Column(name = "dokumentid")
    private Long documentId;
    @Column(name = "datum")
    private String date;
    @Column(name = "zeit")
    private String time;
    @Column(name = "userid")
    private Long userId;
    @Column(name = "dateiname")
    private String filename;
    @Column(name = "dateipfad")
    private String filepath;
    @Column(name = "typ")
    private Integer type;
    @Column(name = "zuordnungen")
    private Integer zuordnungen;

}
