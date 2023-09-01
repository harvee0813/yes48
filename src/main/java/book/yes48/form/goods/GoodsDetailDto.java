package book.yes48.form.goods;

import book.yes48.entity.FileStore;
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

    public GoodsDetailDto(GoodsDetailDto goodsDetailDto) {
        this.id = goodsDetailDto.getId();
        this.name = goodsDetailDto.getName();
        this.author = goodsDetailDto.getAuthor();
        this.price = goodsDetailDto.getPrice();
        this.publisher = goodsDetailDto.getPublisher();
        this.publisherDate = goodsDetailDto.getPublisher();
        this.state = goodsDetailDto.getState();
        this.fileStore = goodsDetailDto.getFileStore();
    }
}
