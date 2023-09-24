package book.yes48.web.form.admin.search;

import book.yes48.entity.FileStore;
import lombok.Getter;
import lombok.Setter;

/**
 * 검색 조건과 타입으로 조회된 상품 리스트를 담는 폼
 */
@Getter @Setter
public class AdminSearchCondition {

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
    private SearchType searchType;

    public AdminSearchCondition(Long id, String name, String sort, String author, String publisher, String publisherDate, Integer price, Integer stockQuantity, String event,
                                String state, FileStore fileStore, String searchBy, SearchType searchType) {
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
        this.searchBy = searchBy;
        this.searchType = searchType;
    }

}
