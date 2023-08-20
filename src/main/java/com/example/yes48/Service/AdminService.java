package com.example.yes48.Service;

import com.example.yes48.domain.FileStore;
import com.example.yes48.domain.goods.Goods;
import com.example.yes48.domain.goods.GoodsSaveForm;
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
    @Autowired
    FileStoreService fileStoreService;

    /**
     * 상품 등록
     * @param form 상품 등록에 사용되는 전용 폼
     */
    @Transactional
    public Long saveGoods(GoodsSaveForm form, MultipartFile file) throws IOException {

        FileStore saveFile = fileStoreService.save(file);

        Goods goods = Goods.builder()
                .name(form.getName())
                .sort(form.getSort())
                .author(form.getAuthor())
                .publisher(form.getPublisher())
                .publisherDate(form.getPublisherDate())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .event(form.getEvent())
                .state(form.getState())
                .fileStore(saveFile)
                .build();

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
            throw new IllegalStateException("이미 존재하는 상품 입니다.");
        }
    }

}
