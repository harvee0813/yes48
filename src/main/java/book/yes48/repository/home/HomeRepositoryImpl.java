package book.yes48.repository.home;

import book.yes48.web.form.goods.GoodsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static book.yes48.entity.goods.QGoods.goods;

public class HomeRepositoryImpl implements HomeRepositoryCustom{

    @Autowired
    JPAQueryFactory queryFactory;

    /**
     * 편집장의 추천 도서 4권 수정일 기준 desc 선별
     */
    @Override
    public List<GoodsDto> selectFourSuggestionBooks() {
        List<GoodsDto> dtos = queryFactory
                .select(Projections.constructor(GoodsDto.class,
                        goods.id, goods.name, goods.author, goods.price, goods.fileStore))
                .from(goods)
                .join(goods.fileStore)
                .where(goods.event.eq("Y"))
                .limit(4)
                .orderBy(goods.lastModifiedDate.desc())
                .fetch();

        return dtos;
    }

    /**
     * 최신 도서 4권 id 기준 desc 선별
     */
    @Override
    public List<GoodsDto> selectFourNewBooks() {
        List<GoodsDto> dtos = queryFactory
                .select(Projections.constructor(GoodsDto.class,
                        goods.id, goods.name, goods.author, goods.price, goods.fileStore))
                .from(goods)
                .join(goods.fileStore)
                .where(goods.sort.contains("도서"))
                .limit(4)
                .orderBy(goods.id.desc())
                .fetch();

        return dtos;
    }

    /**
     * 최신 음반 3장 id 기준 desc 선별
     */
    @Override
    public List<GoodsDto> selectThreeMusic() {
        List<GoodsDto> dtos = queryFactory
                .select(Projections.constructor(GoodsDto.class,
                        goods.id, goods.name, goods.author, goods.price, goods.fileStore))
                .from(goods)
                .join(goods.fileStore)
                .where(goods.sort.eq("음반"))
                .limit(3)
                .orderBy(goods.id.desc())
                .fetch();

        return dtos;
    }

}
