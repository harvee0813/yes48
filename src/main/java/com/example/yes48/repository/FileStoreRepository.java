package com.example.yes48.repository;

import com.example.yes48.domain.FileStore;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileStoreRepository {

    @Autowired
    private final EntityManager em;

    public Long save(FileStore file) {
        em.persist(file);
        return file.getId();
    }
}
