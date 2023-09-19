package book.yes48.web.form.goods;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoodsDetailDto {

    private Long id;
    private String name;
    private String author;
    private Integer price;
    private String publisher;
    private String publisherDate;
    private String state;
    private FileStore fileStore;

    public GoodsDetailDto(Goods goods) {
        this.id = goods.getId();
        this.name = goods.getName();
        this.author = goods.getAuthor();
        this.price = goods.getPrice();
        this.publisher = goods.getPublisher();
        this.publisherDate = goods.getPublisher();
        this.state = goods.getState();
        this.fileStore = goods.getFileStore();
    }
}
