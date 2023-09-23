package book.yes48.repository.goods;

import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsRepositoryCustom {

    @Query("select g from Goods g where g.id = :id")
    Goods findGoodsById(@Param("id") String goodsId);

    @Query("select g from Goods g where g.id = :id")
    List<Goods> findGoodsListById(@Param("id") String goodsId);
}
