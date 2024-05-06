package de.asiegwarth.jwtaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.asiegwarth.jwtaws.entity.Document;
import jakarta.transaction.Transactional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Transactional
    @Procedure(procedureName = "verzeichneDokument")
    void createUploadEntry(
        @Param("datum_in") String datum,
        @Param("zeit_in") String zeit,
        @Param("userid_in") Long userid,
        @Param("dateiname_in") String dateiname,
        @Param("dateipfad_in") String dateipfad,
        @Param("typ_in") int typ
    );

    @Transactional
    @Procedure(procedureName = "verzeichnePic")
    void createUploadEntryForPic(
        @Param("datum_in") String datum,
        @Param("zeit_in") String zeit,
        @Param("userid_in") Long userid,
        @Param("dateiname_in") String dateiname,
        @Param("dateipfad_in") String dateipfad
    );

}
