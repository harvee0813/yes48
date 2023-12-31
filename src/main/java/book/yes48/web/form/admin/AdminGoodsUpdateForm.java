package book.yes48.web.form.admin;

import book.yes48.entity.FileStore;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

/**
 * 상품 수정 폼
 */
@Getter
public class AdminGoodsUpdateForm {

    private Long id;

    @NotBlank(message = "상품 이름을 입력해주세요")
    private String name;
    private String sort;

    @NotBlank(message = "저자를 입력해주세요")
    private String author;

    @NotBlank(message = "촐판사를 입력해주세요")
    private String publisher;

    @NotBlank(message = "출판일을 입력해주세요")
    private String publisherDate;

    @Range(min = 1000, max = 200000, message = "가격은 1000원이상 200,000원이하로 입력해주세요.")
    private int price;

    @Range(min = 0, max = 200, message = "수량은 최대 200개로 입력해주세요.")
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
}
