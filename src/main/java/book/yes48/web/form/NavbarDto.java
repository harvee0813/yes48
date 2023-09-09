package book.yes48.web.form;

import book.yes48.entity.FileStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NavbarDto {

    private Long id;
    private String name;
    private String author;
    private Integer price;
    private FileStore fileStore;

    public NavbarDto(NavbarDto navbarDto) {
        this.id = navbarDto.getId();
        this.name = navbarDto.getName();
        this.author = navbarDto.getAuthor();
        this.price = navbarDto.getPrice();
        this.fileStore = navbarDto.getFileStore();
    }
}
