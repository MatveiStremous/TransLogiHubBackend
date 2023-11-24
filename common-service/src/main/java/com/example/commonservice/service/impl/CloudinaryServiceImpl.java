package com.example.commonservice.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    private final String UPLOAD_FILE_ERROR = "Can't upload file.";
    private final String DELETE_FILE_ERROR = "Can't delete file.";

    @Override
    public String upload(MultipartFile fileToUpload, String folderName) {
        try {
            File file = convertMultipartFileToFile(fileToUpload);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "trans-logi-hub"));
            String imageUrl = (String) uploadResult.get("url");
            file.delete();
            return imageUrl;
        } catch (IOException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, UPLOAD_FILE_ERROR);
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, DELETE_FILE_ERROR);
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp", null);
        try (var inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return file;
    }
}
