package leets.weeth.domain.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    public List<String> uploadFiles(List<MultipartFile> files) {
        // 다중 업로드 && 리스트 ","을 기준으로 하나의 문자열 반환
        if (files == null || files.isEmpty()) {
            return List.of();
        }

        return files.parallelStream()
                .map(file -> {
                    java.io.File fileObj = convertMultiPartFileToFile(file);
                    String fileName = getFileName(file);
                    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(fileName)
                            .build();

                    s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromFile(fileObj));
                    fileObj.delete();

                    return extractUrl(fileName);
                })
                .toList();
    }

    private static String getFileName(MultipartFile file) {
        return UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
    }

    private String extractUrl(String fileName) {
        return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
    }

    private java.io.File convertMultiPartFileToFile(MultipartFile file) {
        java.io.File convertedFile = new java.io.File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    private static String getFileExtension(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }
}
