package Book.yes48.form.admin;

import Book.yes48.Entity.FileStore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Getter
public class AdminGoodsUpdateForm {

    private Long id;
    private String name;
    private String sort;
    private String author;
    private String publisher;
    private String publisherDate;
    private int price;
    private int stockQuantity;
    private String event;
    private String state;
    private FileStore fileStore;

    @Builder
    public AdminGoodsUpdateForm(Long id, String name, String sort, String author, String publisher,
                                String publisherDate, int price, int stockQuantity, String event, String state, FileStore fileStore) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.author = author;
        this.publisher = publisher;
        this.publisherDate = publisherDate;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.event = event;
        this.state = state;
        this.fileStore = fileStore;
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
