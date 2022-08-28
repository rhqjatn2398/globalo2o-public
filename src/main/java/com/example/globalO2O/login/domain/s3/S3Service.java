package com.example.globalO2O.login.domain.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;

    public void upload(String dir, String fileName, String base64Img) {
        byte[] bI = Base64.getDecoder().decode(base64Img.substring(base64Img.indexOf(",") + 1));
        InputStream fis = new ByteArrayInputStream(bI);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bI.length);
        objectMetadata.setContentType("img");

        amazonS3Client.putObject(BUCKET, dir + "/" + fileName, fis, objectMetadata);
        log.info("이미지 파일이 저장되었습니다 경로: {}", amazonS3Client.getUrl(BUCKET, dir + "/" + fileName).toString());
    }

    public String download(String dir, String fileName) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(BUCKET, dir + "/" + fileName);

        return Base64.getEncoder().encodeToString(IOUtils.toByteArray(s3Object.getObjectContent()));
    }

    public void delete(String dir, String fineName) {
        amazonS3Client.deleteObject(BUCKET, dir + "/" + fineName);
    }
}