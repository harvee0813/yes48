package Book.yes48.repository.admin;

import Book.yes48.Entity.FileStore;
import Book.yes48.Entity.goods.Goods;
import Book.yes48.Entity.goods.QGoods;
import Book.yes48.Entity.goods.form.AdminGoodsDto;
import Book.yes48.Entity.goods.form.AdminGoodsSearchDto;
import Book.yes48.Entity.goods.form.AdminSearchCondition;
import Book.yes48.Entity.goods.form.QAdminGoodsDto;
import com.querydsl.core.types.Expression;
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
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public AdminGoodsDto getId(Long id) {
       AdminGoodsDto dto = queryFactory
                .select(new QAdminGoodsDto(goods.id, goods.name, goods.sort, goods.author,
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
                .select(new QAdminGoodsDto(goods.id, goods.name, goods.sort, goods.author,
                        goods.publisher, goods.publisherDate, goods.price,
                        goods.stockQuantity, goods.event, goods.state, goods.fileStore)
                )
                .from(goods)
                .orderBy(goods.id.desc())
                .fetch();

        return dtos;
    }

    @Override
    public Page<AdminGoodsDto> searchPageComplex(AdminGoodsSearchDto goodsSearchDto, AdminSearchCondition condition, Pageable pageable) {
        QGoods goods = QGoods.goods;

        List<AdminGoodsDto> fetch = queryFactory
                .select(new QAdminGoodsDto(
                        goods.id, goods.name, goods.sort, goods.author,
                        goods.publisher, goods.publisherDate, goods.price,
                        goods.stockQuantity, goods.event, goods.state, goods.fileStore
                    )
                )
                .from(goods)
                .where(
                        nameEq(condition.getName()),
                        sortEq(condition.getSort()),
                        authorEq(condition.getAuthor()),
                        publisherEq(condition.getPublisher()),
                        publisherDateEq(condition.getPublisherDate()),
                        priceGoe(condition.getPrice()),
                        stockQuantityLoe(condition.getStockQuantity()),
                        eventEq(condition.getEvent()),
                        stateEq(condition.getState()),
                        fileStoreEq(condition.getFileStore())
                )
//                .where(searchByLike(goodsSearchDto.getSearchQuery(), goodsSearchDto.getSearchBy()))
                .orderBy(goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Goods> count = queryFactory
                .selectFrom(goods)
                .where(
                        nameEq(condition.getName()),
                        sortEq(condition.getSort()),
                        authorEq(condition.getAuthor()),
                        publisherEq(condition.getPublisher()),
                        publisherDateEq(condition.getPublisherDate()),
                        priceGoe(condition.getPrice()),
                        stockQuantityLoe(condition.getStockQuantity()),
                        eventEq(condition.getEvent()),
                        stateEq(condition.getState()),
                        fileStoreEq(condition.getFileStore())
                );

        return PageableExecutionUtils.getPage(fetch, pageable, () -> count.fetch().size());
    }

//    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
//        switch (searchBy) {
//            case "name" : return goods.name.like("%" + searchQuery + "%");
//            case "state" : return goods.state.like("%" + searchQuery + "%");
//            case "event" : return goods.event.like("%" + searchQuery + "%");
//            default: return null;
//        }
//    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? goods.name.eq(name): null;
    }

    private BooleanExpression sortEq(String sort) {
        return hasText(sort) ? goods.name.eq(sort): null;
    }

    private BooleanExpression authorEq(String author) {
        return hasText(author) ? goods.name.eq(author): null;
    }

    private BooleanExpression publisherEq(String publisher) {
        return hasText(publisher) ? goods.name.eq(publisher): null;
    }

    private BooleanExpression publisherDateEq(String publisherDate) {
        return hasText(publisherDate) ? goods.name.eq(publisherDate): null;
    }

    private BooleanExpression priceGoe(Integer priceGoe) {
        return priceGoe != null ? goods.price.goe(priceGoe) : null;
    }

    private BooleanExpression stockQuantityLoe(Integer stockQuantityLoe) {
        return stockQuantityLoe != null ? goods.stockQuantity.loe(stockQuantityLoe) : null;
    }

    private BooleanExpression eventEq(String eventEq) {
        return hasText(eventEq) ? goods.name.eq(eventEq): null;
    }

    private BooleanExpression stateEq(String stateEq) {
        return hasText(stateEq) ? goods.name.eq(stateEq): null;
    }

    private BooleanExpression fileStoreEq(FileStore fileStoreEq) {
        return hasText((CharSequence) fileStoreEq) ? goods.name.eq((Expression<? super String>) fileStoreEq): null;
    }
}
