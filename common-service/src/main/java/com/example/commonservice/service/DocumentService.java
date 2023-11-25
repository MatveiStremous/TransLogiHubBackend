package com.example.commonservice.service;

import com.example.commonservice.dto.DocumentRequest;
import com.example.commonservice.dto.DocumentResponse;
import com.example.commonservice.model.enums.DocumentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    DocumentResponse add(MultipartFile multipartFile, DocumentRequest documentRequest);

    void deleteById(Integer documentId);

    List<DocumentResponse> getAllByEntityIdAndType(Integer entityId, DocumentType type);
}
