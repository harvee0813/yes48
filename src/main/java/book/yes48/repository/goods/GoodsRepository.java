package book.yes48.repository.goods;

import book.yes48.entity.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsRepositoryCustom {

    /**
     * 상품 아이디로 상품 찾기
     * @param goodsId 상품 아이디
     * @return 찾은 상품
     */
    @Query("select g from Goods g where g.id = :id")
    Goods findGoodsById(@Param("id") String goodsId);
}
