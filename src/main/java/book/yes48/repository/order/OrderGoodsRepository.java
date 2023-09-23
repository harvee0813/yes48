package book.yes48.repository.order;

import book.yes48.entity.order.OrderGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {

    @Query("select og from OrderGoods og where og.member.userId = :userId and og.state = :state")
    List<OrderGoods> findOrderGoodsByUserId(@Param("userId") String userId, @Param("state") String state);

    @Query("select og from OrderGoods og where og.member.id = :memberId and og.state = :state")
    List<OrderGoods> findAllByMemberId(@Param("memberId") String memberId, @Param("state") String state);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "delete from OrderGoods og where og.member.id = :memberPkId and og.state = :state")
    void deleteByMemberId(@Param("memberPkId") String memberPkId, @Param("state") String state);

    @Query("select og from OrderGoods og where og.goods.id = :goodsId")
    OrderGoods findByGoodsId(@Param("goodsId") String goodsId);
}
