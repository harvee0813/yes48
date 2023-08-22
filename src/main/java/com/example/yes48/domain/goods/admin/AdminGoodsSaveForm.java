package com.example.yes48.domain.goods.admin;

import com.example.yes48.domain.FileStore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 상품 등록 및 수정 전용 폼
 */
@Getter
public class AdminGoodsSaveForm {

    private String name;
    private String sort;
    private String author;
    private String publisher;
    private String publisherDate;
    private int price;
    private int stockQuantity;
    private String event;
    private String state;

    @Builder
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
    public FileStore file(MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        String originalFilename = file.getOriginalFilename();
        String storeFileName = createdStoreFileName(originalFilename);

        File saveFile = new File(projectPath, storeFileName);
        file.transferTo(saveFile);

        FileStore saveFileStore = FileStore.builder()
                .filename(storeFileName)
                .filepath("/files/" + storeFileName)
                .build();

        return saveFileStore;
    }

    // 서버에 저장되는 파일명
    private String createdStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    // 파일 확장자명 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
