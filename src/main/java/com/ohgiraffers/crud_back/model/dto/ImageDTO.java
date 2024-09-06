package com.ohgiraffers.crud_back.model.dto;

public class ImageDTO {

    private Long id;
    private String fileName;
    private String filePath;
    private String contentType;
    private Long fileSize;

    public ImageDTO() {
    }

    public ImageDTO(Long id, String fileName, String filePath, String contentType, Long fileSize) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
