package com.example.yes48.domain;

import com.example.yes48.domain.goods.Goods;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
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
