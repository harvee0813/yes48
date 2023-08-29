package Book.yes48.repository.admin;

import Book.yes48.Entity.goods.Goods;
import Book.yes48.form.admin.AdminGoodsDto;
import Book.yes48.form.admin.search.AdminGoodsSearch;
import Book.yes48.form.admin.search.SearchType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static Book.yes48.Entity.goods.QGoods.goods;

@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public AdminGoodsDto getId(Long id) {
       AdminGoodsDto dto = queryFactory
                .select(Projections.constructor(AdminGoodsDto.class, goods.id, goods.name, goods.sort, goods.author,
                        goods.publisher, goods.publisherDate, goods.price,
                        goods.stockQuantity, goods.event, goods.state, goods.fileStore)
                )
               .from(goods)
               .join(goods.fileStore)
               .where(goods.id.eq(id))
               .fetchOne();

       return dto;
    }

    @Override
    public List<Goods> findByName(String name) {
        return em.createQuery("select g from Goods g where g.name = :name", Goods.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<AdminGoodsDto> getAll() {
        List<AdminGoodsDto> dtos = queryFactory
                .select(Projections.constructor(AdminGoodsDto.class, goods.id, goods.name, goods.sort, goods.author,
                        goods.publisher, goods.publisherDate, goods.price,
                        goods.stockQuantity, goods.event, goods.state, goods.fileStore)
                )
                .from(goods)
                .orderBy(goods.id.desc())
                .fetch();

        return dtos;
    }

    @Override
    public Page<Goods> findAllPageAndSearch(Pageable pageable, AdminGoodsSearch adminGoodsSearch) {
        List<Goods> result = queryFactory.selectFrom(goods)
                .where(searchTypeAndWord(adminGoodsSearch))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(goods.id.desc())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(goods.count())
                .from(goods)
                .where(searchTypeAndWord(adminGoodsSearch));

        return PageableExecutionUtils.getPage(result, pageable, count::fetchOne);
    }

    private BooleanExpression searchTypeAndWord(AdminGoodsSearch adminGoodsSearch) {
        if (adminGoodsSearch.isEmpty()) return null;

        if (adminGoodsSearch.getSearchType().equals(SearchType.SORT)) {
            return goods.sort.contains(adminGoodsSearch.getSearchBy());
        }
        if (adminGoodsSearch.getSearchType().equals(SearchType.NAME)) {
            return goods.name.contains(adminGoodsSearch.getSearchBy());
        }
        if (adminGoodsSearch.getSearchType().equals(SearchType.STATE)) {
            return goods.state.contains(adminGoodsSearch.getSearchBy());
        }
        return null;
    }
}