package com.example.commonservice.dto;

import com.example.commonservice.model.enums.DocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllEntityDocumentRequest {
    private DocumentType type;
    private Integer entityId;
}
