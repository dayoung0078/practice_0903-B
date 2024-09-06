package com.ohgiraffers.crud_back.view.controller;

import com.ohgiraffers.crud_back.model.entity.Image;
import com.ohgiraffers.crud_back.viewmodel.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // 등록

    @PostMapping("/image") // 이미지의 업로드
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
       //  HTTP 요청의 "file" 파라미터로 전송된 파일을 MultipartFile 객체로 받습니다.
        //  MultipartFile은 Spring에서 제공하는 인터페이스로, 업로드된 파일을 다루는 데 사용됩니다.

        Image image = imageService.storeImage(file);
        //  storeImage - 이 메소드는 파일을 디스크에 저장하고, 데이터베이스에 메타데이터를 저장한 후,
        //    저장된 이미지 정보를 담은 Image 객체를 반환합니다.
        return ResponseEntity.ok(image);
    }

    // 상세조회

    @GetMapping("/image/{id}") //  {id}는 경로 변수로, 실제 요청 시 이 부분에 이미지 ID가 들어감.
    public ResponseEntity<Resource> getImage(@PathVariable Long id) throws IOException {
        Image image = imageService.getImage(id);
        Path path = Paths.get(image.getFilePath());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
        // attachment - 파일을 다운로드하라는 의미 , filename은 다운로드될 파일의 이름을 지정
    }

    //수정
    @PutMapping("/image/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Image updatedImage = imageService.updateImage(id, file);
        return ResponseEntity.ok(updatedImage);
    }

    // 삭제
    @DeleteMapping("/image/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
