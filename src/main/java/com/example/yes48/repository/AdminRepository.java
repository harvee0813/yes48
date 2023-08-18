package com.example.yes48.repository;

import com.example.yes48.domain.goods.Goods;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

// 인프런
@Repository
public class AdminRepository {

    @PersistenceContext
    private EntityManager em;

    // 상품 등록
    public void saveGoods(Goods goods) {
       em.persist(goods);
    }
}
