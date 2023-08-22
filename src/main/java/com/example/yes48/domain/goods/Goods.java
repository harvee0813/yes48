package com.example.yes48.domain.goods;

import com.example.yes48.domain.FileStore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Goods {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(unique = true)
    private String name;
    private String sort;
    private String author;
    private String publisher;
    private String publisherDate;
    private int price;
    private int stockQuantity;
    private String event;
    private String state;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_store_id", unique = true)
    private FileStore fileStore;

    @Builder
    public Goods(String name, String sort, String author,
                 String publisher, String publisherDate,
                 int price, int stockQuantity, String event,
                 String state, FileStore fileStore) {
        this.name = name;
        this.sort = sort;
        this.author = author;
        this.publisher = publisher;
        this.publisherDate = publisherDate;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.fileStore = fileStore;
        this.event = event;
        this.state = state;
    }
}
