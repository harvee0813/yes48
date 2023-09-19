package book.yes48.entity;

import book.yes48.entity.goods.Goods;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class FileStore {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_store_id")
    private Long id;
    private String filename;
    private String filepath;

    @Builder
    public FileStore(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }

}
