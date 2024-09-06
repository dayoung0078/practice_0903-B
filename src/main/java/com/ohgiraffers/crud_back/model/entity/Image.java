package com.ohgiraffers.crud_back.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath; // 서버의 파일 시스템에서 실제 이미지 파일이 저장된 경로
    // 이미지를 클라이언트에게 제공할 때 파일의 정확한 위치를 알아야 합니다.
    // 파일 관리 (예: 삭제, 이동)를 할 때 이 정보가 필요합니다.
    // 데이터베이스와 실제 파일 시스템 간의 연결고리 역할을 합니다.

    @Column(nullable = false)
    private String contentType; // 이미지 파일의 MIME 타입을 저장
    // "image/jpeg", "image/png" 등입니다. 이 정보가 필요한 이유는:
    // 브라우저에게 파일 형식을 알려줘 올바르게 렌더링할 수 있게 합니다.
    // 파일 다운로드 시 적절한 Content-Type 헤더를 설정할 수 있습니다.
    // 허용된 파일 형식만 업로드되었는지 확인하는 데 사용할 수 있습니다.

    // MIME(Multipurpose Internet Mail Extensions) 타입은
    // 파일의 형식을 나타내는 인터넷 표준입니다.
    // 원래는 이메일 시스템을 위해 개발되었지만,
    // 현재는 HTTP를 비롯한 여러 인터넷 프로토콜에서 광범위하게 사용됩니다.

    // 주요 타입들:
    //
    // text: 텍스트 기반 문서 (예: text/plain, text/html)
    // image: 이미지 파일 (예: image/jpeg, image/png, image/gif)
    // audio: 오디오 파일 (예: audio/mpeg, audio/wav)
    // video: 비디오 파일 (예: video/mp4, video/quicktime)
    // application: 이진 데이터 또는 응용 프로그램 관련 데이터
    // (예: application/pdf, application/json)


    @Column(nullable = false)
    private Long fileSize;

    public Image() {
    }

    public Image(Long id, String fileName, String filePath, String contentType, Long fileSize) {
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
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}


