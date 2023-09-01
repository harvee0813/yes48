package book.yes48.form.goods;

import book.yes48.entity.FileStore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoodsDto {

    private Long id;
    private String name;
    private String author;
    private Integer price;
    private String state;
    private FileStore fileStore;

    public GoodsDto(GoodsDto goodsDto) {
        this.id = goodsDto.getId();
        this.name = goodsDto.getName();
        this.author = goodsDto.getAuthor();
        this.price = goodsDto.getPrice();
        this.state = goodsDto.getState();
        this.fileStore = goodsDto.getFileStore();
    }
}
