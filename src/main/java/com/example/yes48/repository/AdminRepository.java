package com.example.yes48.repository;

import com.example.yes48.domain.goods.Goods;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

// 인프런
@Repository
@RequiredArgsConstructor
public class AdminRepository {

    @Autowired
    private final EntityManager em;

    // 상품 등록
    public void saveGoods(Goods goods) {
        em.persist(goods);
    }

    // id로 상품 찾기
    public Goods findOne(Long id) {
        return em.find(Goods.class, id);
    }

    public List<Goods> findByName(String name) {
        return em.createQuery("select g from Goods g where g.name = :name", Goods.class)
                .setParameter("name", name)
                .getResultList();
    }
}