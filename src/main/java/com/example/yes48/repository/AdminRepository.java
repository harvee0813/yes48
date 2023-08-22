package com.example.yes48.repository;

import com.example.yes48.domain.goods.Goods;
import com.example.yes48.domain.goods.admin.AdminGoodsDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    @Autowired
    private final EntityManager em;

    // 상품 등록
    public Long saveGoods(Goods goods) {
        if(goods.getId() == null) {
            em.persist(goods);
        } else {
            em.merge(goods);
        }

        return goods.getId();
    }

    // id로 상품 찾기
    public Goods findOne(Long id) {
        return em.find(Goods.class, id);
    }

    // 상품이름으로 찾기
    public List<Goods> findByName(String name) {
        return em.createQuery("select g from Goods g where g.name = :name", Goods.class)
                .setParameter("name", name)
                .getResultList();
    }

    // 전체 상품 조회
    public List<AdminGoodsDto> findAll() {
        return em.createQuery("select g from Goods g")
                .getResultList();
    }
}