package com.example.yes48.Entity.goods.form;

import com.example.yes48.Entity.FileStore;
import lombok.Builder;
import lombok.Getter;

/**
 * 상품 조회 전용 폼
 */
@Getter
public class AdminGoodsDto {

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
    public AdminGoodsDto(Long id, String name, String sort, String author, String publisher,
                         String publisherDate, int price, int stockQuantity,
                         String event, String state, FileStore fileStore) {
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
