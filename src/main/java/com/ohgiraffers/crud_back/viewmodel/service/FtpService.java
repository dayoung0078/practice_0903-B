package com.ohgiraffers.crud_back.viewmodel.service;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;

@Service // 이 클래스를 Spring의 서비스 빈으로 등록
public class FtpService {

    // 로깅을 위한 Logger 인스턴스 생성
    private static final Logger logger = LoggerFactory.getLogger(FtpService.class);

    // application.yml에서 FTP 설정 값을 주입받음
    @Value("${ftp.server}")
    private String ftpServer;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    // FTP 클라이언트 연결 설정 메서드
    private FTPClient connectClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(5000);  // 연결 타임아웃 5초
        ftpClient.setDataTimeout(5000);     // 데이터 전송 타임아웃 5초

        try {
            ftpClient.connect(ftpServer, ftpPort);
            boolean login = ftpClient.login(ftpUsername, ftpPassword);
            if (!login) {
                throw new IOException("FTP 서버 로그인 실패");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            return ftpClient;
        } catch (IOException e) {
            logger.error("FTP 서버 연결 실패: {}", e.getMessage());
            throw e;
        }
    }

    // 파일 업로드 메서드
    public boolean uploadFile(String remoteFile, InputStream inputStream) {
        FTPClient ftpClient = null;
        try {
            ftpClient = connectClient();
            boolean result = ftpClient.storeFile(remoteFile, inputStream);
            if (result) {
                logger.info("파일 업로드 성공: {}", remoteFile);
            } else {
                logger.error("파일 업로드 실패: {}", remoteFile);
            }
            return result;
        } catch (IOException e) {
            logger.error("파일 업로드 중 오류 발생: {}", e.getMessage());
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    // 파일 다운로드 메서드
    public byte[] downloadFile(String remoteFile) {
        FTPClient ftpClient = null;
        try {
            ftpClient = connectClient();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
            if (success) {
                logger.info("파일 다운로드 성공: {}", remoteFile);
                return outputStream.toByteArray();
            } else {
                logger.error("파일 다운로드 실패: {}", remoteFile);
                return null;
            }
        } catch (IOException e) {
            logger.error("파일 다운로드 중 오류 발생: {}", e.getMessage());
            return null;
        } finally {
            disconnect(ftpClient);
        }
    }

    // 파일 삭제 메서드
    public boolean deleteFile(String remoteFile) {
        FTPClient ftpClient = null;
        try {
            ftpClient = connectClient();
            boolean result = ftpClient.deleteFile(remoteFile);
            if (result) {
                logger.info("파일 삭제 성공: {}", remoteFile);
            } else {
                logger.error("파일 삭제 실패: {}", remoteFile);
            }
            return result;
        } catch (IOException e) {
            logger.error("파일 삭제 중 오류 발생: {}", e.getMessage());
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    // FTP 클라이언트 연결 해제 메서드
    private void disconnect(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("FTP 연결 해제 중 오류 발생: {}", e.getMessage());
            }
        }
    }
}