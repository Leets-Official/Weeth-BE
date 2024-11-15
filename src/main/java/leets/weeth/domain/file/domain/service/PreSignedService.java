package leets.weeth.domain.file.domain.service;

import leets.weeth.domain.file.application.dto.response.FileDto;
import leets.weeth.domain.file.application.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreSignedService {

    private final S3Presigner s3Presigner;
    private final FileMapper fileMapper;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public FileDto generateUrl(String fileName) {
        String key = generateKey(fileName);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        PutObjectPresignRequest request = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedUrlRequest = s3Presigner.presignPutObject(request);

        String putUrl = presignedUrlRequest.url().toString();
        String getUrl = generateGetUrl(key);

        return fileMapper.toFileDto(putUrl, getUrl);
    }

    public String generateGetUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
    }

    // 파일 이름을 고유하게 생성하는 메서드
    private String generateKey(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFileName.replaceAll("\\s+", "_");
    }
}
