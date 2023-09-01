package book.yes48.service;

import book.yes48.form.goods.GoodsDetailDto;
import book.yes48.form.goods.GoodsDto;
import book.yes48.form.goods.GoodsSearch;
import book.yes48.repository.goods.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GoodsService {

    @Autowired GoodsRepository goodsRepository;

    public Page<GoodsDto> domesticBooksFindList(GoodsSearch goodsSearch, Pageable pageable) {
        goodsSearch.setSort("국내 도서"); // 페이징 조건

        Page<GoodsDto> domesticBooks = goodsRepository.findAllBooks(goodsSearch, pageable);
        return domesticBooks.map(GoodsDto::new);
    }

    public Page<GoodsDto> foreignBooksFindList(GoodsSearch goodsSearch, Pageable pageable) {
        goodsSearch.setSort("외국 도서"); // 페이징 조건

        Page<GoodsDto> domesticBooks = goodsRepository.findAllBooks(goodsSearch, pageable);
        return domesticBooks.map(GoodsDto::new);
    }

    public Page<GoodsDto> musicBooksFindList(GoodsSearch goodsSearch, Pageable pageable) {
        goodsSearch.setSort("음반"); // 페이징 조건

        Page<GoodsDto> domesticBooks = goodsRepository.findAllBooks(goodsSearch, pageable);
        return domesticBooks.map(GoodsDto::new);
    }

    public GoodsDetailDto getId(Long goodsId) {
        GoodsDetailDto findGood = goodsRepository.getId(goodsId);
        return findGood;
    }
}
