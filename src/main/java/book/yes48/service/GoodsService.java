package book.yes48.service;

import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.repository.cartItem.CartItemRepository;
import book.yes48.repository.member.MemberRepository;
import book.yes48.repository.myCart.MyCartRepository;
import book.yes48.web.form.goods.GoodsDetailDto;
import book.yes48.web.form.goods.GoodsDto;
import book.yes48.web.form.goods.GoodsSearch;
import book.yes48.repository.goods.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GoodsService {

    @Autowired
    private final GoodsRepository goodsRepository;

    /**
     * 국내 도서 리스트 가져오기
     * @param goodsSearch 검색 조건
     */
    public Page<GoodsDto> domesticBooksFindList(GoodsSearch goodsSearch, Pageable pageable) {
        goodsSearch.setSort("국내 도서"); // 페이징 조건

        Page<GoodsDto> domesticBooks = goodsRepository.findAllBooks(goodsSearch, pageable);
        return domesticBooks.map(GoodsDto::new);
    }

    /**
     * 외국 도서 리스트 가져오기
     * @param goodsSearch 검색 조건
     */
    public Page<GoodsDto> foreignBooksFindList(GoodsSearch goodsSearch, Pageable pageable) {
        goodsSearch.setSort("외국 도서"); // 페이징 조건

        Page<GoodsDto> domesticBooks = goodsRepository.findAllBooks(goodsSearch, pageable);
        return domesticBooks.map(GoodsDto::new);
    }

    /**
     * 음반 리스트 가져오기
     * @param goodsSearch 검색 조건
     */
    public Page<GoodsDto> musicFindList(GoodsSearch goodsSearch, Pageable pageable) {
        goodsSearch.setSort("음반"); // 페이징 조건

        Page<GoodsDto> domesticBooks = goodsRepository.findAllBooks(goodsSearch, pageable);
        return domesticBooks.map(GoodsDto::new);
    }

    /**
     * 상품 아이디로 상품 찾기
     * @param goodsId
     * @return
     */
    public GoodsDetailDto getId(Long goodsId) {
        GoodsDetailDto findGood = goodsRepository.getId(goodsId);
        return findGood;
    }
}
