package com.example.commonservice.controller;

import com.example.commonservice.dto.DocumentRequest;
import com.example.commonservice.dto.DocumentResponse;
import com.example.commonservice.model.enums.DocumentType;
import com.example.commonservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("common/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public DocumentResponse addFile(@RequestParam("file") MultipartFile multipartFile,
                                    @RequestPart("documentRequest") DocumentRequest documentRequest) {
        return documentService.add(multipartFile, documentRequest);
    }

    @DeleteMapping("/{documentId}")
    public void deleteFile(@PathVariable Integer documentId) {
        documentService.deleteById(documentId);
    }

    @GetMapping
    public List<DocumentResponse> getAllEntityDocuments(@RequestParam("entityId") Integer entityId,
                                                        @RequestParam("type") DocumentType type) {
        return documentService.getAllByEntityIdAndType(entityId, type);
    }
}

