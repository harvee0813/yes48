package Book.yes48.Entity;

import Book.yes48.Entity.goods.Goods;
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

    @OneToOne(mappedBy = "fileStore", fetch = FetchType.LAZY)
    private Goods goods;

    @Builder
    public FileStore(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }

}
