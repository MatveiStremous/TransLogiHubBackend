package com.example.commonservice.service.impl;

import com.example.commonservice.dto.DocumentRequest;
import com.example.commonservice.dto.DocumentResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Document;
import com.example.commonservice.model.enums.DocumentType;
import com.example.commonservice.repository.DocumentRepository;
import com.example.commonservice.service.CloudinaryService;
import com.example.commonservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final CloudinaryService cloudinaryService;
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    @Override
    public DocumentResponse add(MultipartFile multipartFile, DocumentRequest documentRequest) {
        String folderName = documentRequest.getType().toString().toLowerCase();
        String url = cloudinaryService.upload(multipartFile, folderName);
        Document document = new Document();
        document.setName(documentRequest.getName());
        document.setType(documentRequest.getType());
        document.setEntityId(documentRequest.getEntityId());
        document.setUrl(url);
        document.setIsActive(true);
        document = documentRepository.save(document);
        return modelMapper.map(document, DocumentResponse.class);
    }

    @Override
    public void deleteById(Integer documentId) {
        Document documentFromDb = getById(documentId);
        if (!documentFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, "DOC-4");
        } else {
            documentFromDb.setIsActive(false);
            documentRepository.save(documentFromDb);
        }
    }

    @Override
    public List<DocumentResponse> getAllByEntityIdAndType(Integer entityId, DocumentType type) {
        return documentRepository.findAllByIsActiveTrueAndEntityIdAndType(entityId, type)
                .stream()
                .map(doc -> modelMapper.map(doc, DocumentResponse.class))
                .toList();
    }

    private Document getById(Integer id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "DOC-3"));
    }
}
