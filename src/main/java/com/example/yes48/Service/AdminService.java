package com.example.yes48.Service;

import com.example.yes48.domain.FileStore;
import com.example.yes48.domain.goods.Goods;
import com.example.yes48.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminService {

    @Autowired
    private AdminRepository goodsRepository;

    /**
     * 상품 등록
     * @param goods 상품 domain
     */
    @Transactional
    public Long saveGoods(Goods goods) throws IOException {

        validateDuplicationGoods(goods);

        goodsRepository.saveGoods(goods);

        return goods.getId();
    }

    // 상품 아이디 조회
    public Goods findOne(Long goodsId) {
        return goodsRepository.findOne(goodsId);
    }

    // 중복 상품 검증
    public void validateDuplicationGoods (Goods goods) {

        List<Goods> findGoods = goodsRepository.findByName(goods.getName());
        if (!findGoods.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 상품입니다.");
        }
    }

}
