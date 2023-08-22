package com.example.yes48.domain.goods.admin;

import lombok.Getter;

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
}
