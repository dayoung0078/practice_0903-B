package com.ohgiraffers.crud_back.viewmodel.service;

import com.ohgiraffers.crud_back.exception.ResourceNotFoundException;
import com.ohgiraffers.crud_back.model.entity.Image;
import com.ohgiraffers.crud_back.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${ftp.upload-dir}")
    private String ftpUploadDir;

    private final ImageRepository imageRepository;
    private final FtpService ftpService;

    @Autowired
    public ImageService(ImageRepository imageRepository, FtpService ftpService) {
        this.imageRepository = imageRepository;
        this.ftpService = ftpService;
    }

    public Image storeImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID() + "_" + fileName;
        String filePath = ftpUploadDir + "/" + uniqueFileName;

        boolean uploadSuccess = ftpService.uploadFile(filePath, file.getInputStream());

        if (!uploadSuccess) {
            throw new IOException("Failed to upload file to FTP server");
        }

        Image image = new Image();
        image.setFileName(fileName);
        image.setFilePath(filePath);
        image.setContentType(file.getContentType());
        image.setFileSize(file.getSize());

        return imageRepository.save(image);
    }

    public Image getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    public Resource getImageFileFromFtp(Image image) {
        byte[] fileData = ftpService.downloadFile(image.getFilePath());
        return new ByteArrayResource(fileData);
    }

    public Image updateImage(Long id, MultipartFile file) throws IOException {
        Image image = getImage(id);

        // Delete old file
        ftpService.deleteFile(image.getFilePath());

        // Upload new file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID() + "_" + fileName;
        String filePath = ftpUploadDir + "/" + uniqueFileName;

        boolean uploadSuccess = ftpService.uploadFile(filePath, file.getInputStream());

        if (!uploadSuccess) {
            throw new IOException("Failed to upload file to FTP server");
        }

        image.setFileName(fileName);
        image.setFilePath(filePath);
        image.setContentType(file.getContentType());
        image.setFileSize(file.getSize());

        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        Image image = getImage(id);
        boolean deleteSuccess = ftpService.deleteFile(image.getFilePath());

        if (!deleteSuccess) {
            throw new RuntimeException("Failed to delete file from FTP server");
        }

        imageRepository.delete(image);
    }

}