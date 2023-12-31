package book.yes48.repository.goods;

import book.yes48.web.form.goods.GoodsDetailDto;
import book.yes48.web.form.goods.GoodsDto;
import book.yes48.web.form.goods.GoodsSearch;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static book.yes48.entity.goods.QGoods.goods;

@Slf4j
@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepositoryCustom {

    @Autowired
    JPAQueryFactory queryFactory;

    /**
     * 검색어에 따른 책 조회
     * @param goodsSearch 검색 조건
     * @return
     */
    @Override
    public Page<GoodsDto> findAllBooks(GoodsSearch goodsSearch, Pageable pageable) {
        List<GoodsDto> result = queryFactory
                .select(Projections.constructor(GoodsDto.class,
                       goods.id, goods.name, goods.author, goods.price, goods.fileStore))
                .from(goods)
                .where(goods.state.eq("Y"))
                .where(searchStateAndSort(goodsSearch))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(goods.id.desc())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(goods.count())
                .from(goods)
                .where(searchStateAndSort(goodsSearch));

        return PageableExecutionUtils.getPage(result, pageable, count::fetchOne);
    }

    /**
     * 아이디로 상품 가져오기
     * @param id 상품 아이디
     * @return
     */
    @Override
    public GoodsDetailDto getId(Long id) {
        GoodsDetailDto dto = queryFactory
                .select(Projections.constructor(GoodsDetailDto.class,
                        goods.id, goods.name, goods.author, goods.price, goods.publisher, goods.publisherDate, goods.state, goods.fileStore))
                .from(goods)
                .join(goods.fileStore)
                .where(goods.id.eq(id))
                .fetchOne();

        return dto;
    }

    // 검색 종류
    private BooleanExpression searchStateAndSort(GoodsSearch goodsSearch) {
        if (goodsSearch.isEmpty()) return null;

        if (goodsSearch.getSort().equals("국내 도서")) {
            return goods.sort.eq("국내 도서");
        }
        if (goodsSearch.getSort().equals("외국 도서")) {
            return goods.sort.eq("외국 도서");
        }
        if (goodsSearch.getSort().equals("음반")) {
            return goods.sort.eq("음반");
        }
        return null;
    }
}
