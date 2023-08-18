package com.example.yes48.domain.goods;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Goods {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;
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

    @Builder
    public Goods(String name, String sort, String filename,
                 String filepath, String author, String publisher,
                 String publisherDate, int price, int stockQuantity,
                 String event, String state) {
        this.name = name;
        this.sort = sort;
        this.filename = filename;
        this.filepath = filepath;
        this.author = author;
        this.publisher = publisher;
        this.publisherDate = publisherDate;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.event = event;
        this.state = state;
    }
}
