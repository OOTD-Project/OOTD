package com.sparta_a5.ootd.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Util {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String uploadImage(String classification, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            //파일명 중복 방지를 위한 UUID 생성
            String filename = UUID.randomUUID() + getExtension(Objects.requireNonNull(file.getOriginalFilename()));

            //S3 업로드 요청
            amazonS3Client.putObject(bucket + classification, filename, file.getInputStream(), metadata);

            return filename;
        } catch(IOException e) {
            e.printStackTrace(); //custom exception 변경 필요
            return null;
        }
    }

    public void deleteImage(String classification, String filename) {
        amazonS3Client.deleteObject(bucket + classification, filename);
    }

    //이미지 접근을 위한 URL 생성
    public String getImageURL(String classification, String filename) {
        return S3Const.S3_BASEURL + classification + filename;
    }

    //확장자 추출 함수
    private String getExtension(String originalFilename) {
        int idx = originalFilename.lastIndexOf('.');
        return originalFilename.substring(idx+1);
    }
}