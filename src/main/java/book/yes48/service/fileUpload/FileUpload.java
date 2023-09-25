package book.yes48.service.fileUpload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUpload {

    @Autowired
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 클라우드 업로드
    public String uploadFileToS3(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String finalFileName = changedFileName(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("files/" + ext);

        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                    bucket, finalFileName, file.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new IOException("파일 예외 발생");
        }
        return amazonS3.getUrl(bucket, finalFileName).toString();
    }

    public String changedFileName(String originalFilename) {
        String random = UUID.randomUUID().toString();
        return random  + originalFilename;
    }
}
