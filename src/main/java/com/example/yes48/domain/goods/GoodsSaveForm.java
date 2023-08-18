package com.example.yes48.domain.goods;

import lombok.Data;

@Data
public class GoodsSaveForm {

    private String name;
    private String sort;
    private String filename;
    private String filepath;
    private String author;
    private String publisher;
    private String publisherDate;
    private int price;
    private int stockQuantity;
    private String event;
    private String state;
}
