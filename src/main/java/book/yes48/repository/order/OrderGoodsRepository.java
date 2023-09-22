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

    @Query("select og from OrderGoods og where og.member.userId = :userId")
    List<OrderGoods> findOrderGoodsByUserId(@Param("userId") String userId);

    @Query("select og from OrderGoods og where og.member.id = :memberId")
    List<OrderGoods> findAllByMemberId(@Param("memberId") String memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "delete from OrderGoods where member.id = :memberPkId")
    void deleteByMemberId(@Param("memberPkId") String memberPkId);
}
