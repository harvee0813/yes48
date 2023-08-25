package Book.yes48.Entity.goods;

import Book.yes48.Entity.BaseTimeEntity;
import Book.yes48.Entity.FileStore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Goods extends BaseTimeEntity {

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

    /**
     * 상품 등록
     */
    @Builder
    public Goods(String name, String sort, String author, String publisher, String publisherDate,
                 int price, int stockQuantity, String event, String state, FileStore fileStore) {
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

    // fileStore 없을 때
    public void updateGoods(Long id, String name, String sort, String author, String publisher, String publisherDate,
                            int price, int stockQuantity, String event, String state) {
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
    }

    // fileStore 있을 때
    public void updateGoodsAndFile(Long id, String name, String sort, String author, String publisher, String publisherDate,
                 int price, int stockQuantity, String event, String state, FileStore fileStore) {
        this.id = id;
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
