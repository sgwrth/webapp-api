package de.asiegwarth.jwtaws.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.asiegwarth.jwtaws.repository.DocumentRepository;

public interface DocumentService {
    
    @Service
    public class DocumentServiceImpl implements DocumentService {

        @Autowired
        DocumentRepository documentRepository;

        @Override
        public void createUploadEntry(
                LocalDate datum,
                LocalTime zeit,
                Long userid,
                String dateiname,
                String dateipfad,
                int typ
        ) {
            documentRepository.createUploadEntry(
                datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                zeit.format(DateTimeFormatter.ofPattern("HH:mm")),
                userid,
                dateiname,
                dateipfad,
                typ
            );
        }

        @Override
        public void createUploadEntryForPic(
                LocalDate datum,
                LocalTime zeit,
                Long userid,
                String dateiname,
                String dateipfad
        ) {
            documentRepository.createUploadEntryForPic(
                datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                zeit.format(DateTimeFormatter.ofPattern("HH:mm")),
                userid,
                dateiname,
                dateipfad
            );
        }
    }

    public void createUploadEntry(
        LocalDate datum,
        LocalTime zeit,
        Long userid,
        String dateiname,
        String dateipfad,
        int typ
    );

    public void createUploadEntryForPic(
        LocalDate datum,
        LocalTime zeit,
        Long userid,
        String dateiname,
        String dateipfad
    );

}
