package com.example.commonservice.repository;

import com.example.commonservice.model.Document;
import com.example.commonservice.model.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findAllByIsActiveTrueAndEntityIdAndType(Integer entityId, DocumentType type);
}
