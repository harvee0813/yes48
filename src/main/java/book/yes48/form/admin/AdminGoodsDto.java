package book.yes48.form.admin;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.admin.search.SearchType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상품 조회 전용 화면
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
    private SearchType searchType;

    public AdminGoodsDto(Long id, String name, String sort, String author, String publisher, String publisherDate,
                         int price, int stockQuantity, String event, String state, String searchBy, SearchType searchType) {
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
        this.searchType = searchType;
    }

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

    public AdminGoodsDto(AdminGoodsDto adminGoodsDto) {
        this.id = adminGoodsDto.getId();
        this.name = adminGoodsDto.getName();
        this.sort = adminGoodsDto.getSort();
        this.author = adminGoodsDto.getAuthor();
        this.publisher = adminGoodsDto.getPublisher();
        this.publisherDate = adminGoodsDto.getPublisherDate();
        this.price = adminGoodsDto.getPrice();
        this.stockQuantity = adminGoodsDto.getStockQuantity();
        this.event = adminGoodsDto.getEvent();
        this.state = adminGoodsDto.getState();
        this.fileStore = adminGoodsDto.getFileStore();
    }
}
