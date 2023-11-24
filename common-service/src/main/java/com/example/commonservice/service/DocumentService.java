package com.example.commonservice.service;

import com.example.commonservice.dto.AllEntityDocumentRequest;
import com.example.commonservice.dto.DocumentRequest;
import com.example.commonservice.dto.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    DocumentResponse add(MultipartFile multipartFile, DocumentRequest documentRequest);

    List<DocumentResponse> getAllByEntityId(AllEntityDocumentRequest allEntityDocumentRequest);

    void deleteById(Integer documentId);
}
