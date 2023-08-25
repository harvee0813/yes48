package Book.yes48.Entity.goods.form;

import Book.yes48.Entity.FileStore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

/**
 * 상품 조회 전용 폼
 */
@RequiredArgsConstructor
@Getter
public class AdminGoodsDto {

    private Long id;
    private String name;
    private String sort;
    private String author;
    private String publisher;
    private String publisherDate;
    private Integer price;
    private Integer stockQuantity;
    private String event;
    private String state;
    private FileStore fileStore;
    private String searchBy;
    private String searchQuery = "";

    public AdminGoodsDto(Long id, String name, String sort, String author, String publisher, String publisherDate,
                         int price, int stockQuantity, String event, String state, String searchBy, String searchQuery) {
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
        this.searchBy = searchBy;
        this.searchQuery = searchQuery;
    }

    @QueryProjection
    public AdminGoodsDto(Long id, String name, String sort, String author, String publisher, String publisherDate, int price,
                         int stockQuantity, String event, String state, FileStore fileStore) {
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
