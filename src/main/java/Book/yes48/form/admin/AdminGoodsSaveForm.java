package Book.yes48.form.admin;

import Book.yes48.Entity.FileStore;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 상품 등록, 수정 폼
 */
@Getter
public class AdminGoodsSaveForm {

    @NotNull
    @NotBlank(message = "상품 이름을 입력해주세요")
    private String name;
    private String sort;

    @NotNull
    @NotBlank(message = "저자를 입력해주세요")
    private String author;

    @NotNull
    @NotBlank(message = "촐판사를 입력해주세요")
    private String publisher;

    @NotNull
    @NotBlank(message = "출판일을 입력해주세요")
    private String publisherDate;

    @Range(min = 1000, max = 200000)
    private int price;

    @Max(value = 200)
    private int stockQuantity;

    private String event;
    private String state;

    @Builder
    @QueryProjection
    public AdminGoodsSaveForm(String name, String sort, String author, String publisher, String publisherDate, int price, int stockQuantity, String event, String state) {
        this.name = name;
        this.sort = sort;
        this.author = author;
        this.publisher = publisher;
        this.publisherDate = publisherDate;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.event = event;
        this.state = state;
    }
    
    // 파일 업로드
    String projectPath = "C:\\upload\\";

    public String getFullPath(String filename) {
        return projectPath + filename;
    }

    public FileStore file(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(fileName)));

        FileStore saveFileStore = FileStore.builder()
                .filename(fileName)
                .filepath("C:/upload/" + fileName)
                .build();

        return saveFileStore;
    }
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
