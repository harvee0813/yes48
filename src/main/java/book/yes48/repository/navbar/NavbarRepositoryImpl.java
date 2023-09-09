package book.yes48.repository.navbar;

import book.yes48.web.form.NavbarDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static book.yes48.entity.goods.QGoods.goods;

public class NavbarRepositoryImpl implements NavbarRepositoryCustom{

    @Autowired
    JPAQueryFactory queryFactory;

    /**
     * nabvar에 있는 검색창
     * @param pageable 검색된 상품 페이징
     * @param search 검색어
     * @return  검색 결과 반환
     */
    @Override
    public Page<NavbarDto> goodsSearchList(Pageable pageable, String search) {
        List<NavbarDto> result = queryFactory
                .select(Projections.constructor(NavbarDto.class,
                        goods.id, goods.name, goods.author, goods.price, goods.fileStore))
                .from(goods)
                .where(goods.state.eq("Y"))
                .where(goods.name.contains(search)
                        .or(goods.author.contains(search))
                        .or(goods.publisher.contains(search)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(goods.id.desc())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(goods.count())
                .from(goods)
                .where(goods.name.contains(search)
                        .or(goods.author.contains(search))
                        .or(goods.publisher.contains(search)));

        return PageableExecutionUtils.getPage(result, pageable, count::fetchOne);
    }
}
