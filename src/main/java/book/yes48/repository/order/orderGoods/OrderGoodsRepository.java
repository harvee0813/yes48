package book.yes48.repository.order.orderGoods;

import book.yes48.entity.order.OrderGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long>, OrderGoodsRepositoryCustom {

    /**
     * 유저 아이디와 orderGoods에 등록된 상품 상태로 주문 대기상품 조회
     * @param userId 유저 아이디
     * @param state 주문 대기 상품 상태 (WAIT or ORDER)
     * @return 주문 대기 상품 리스트
     */
    @Query("select og from OrderGoods og where og.member.userId = :userId and og.state = :state")
    List<OrderGoods> findOrderGoodsByUserId(@Param("userId") String userId, @Param("state") String state);

    /**
     * 멤버 아이디와 상태로 등록된 주문 상품 조회
     * @param memberId 유저 아이디
     * @param state 주문 대기 상품 상태 (WAIT or ORDER)
     * @return 주문 대기 상품 리스트
     */
    @Query("select og from OrderGoods og where og.member.id = :memberId and og.state = :state")
    List<OrderGoods> findAllByMemberId(@Param("memberId") String memberId, @Param("state") String state);

    /**
     * 주문 대기 상품 상태와 멤버 고유 아이디로 주문 대기 상품 삭제
     * @param memberPkId 멤버 고유 아이디
     * @param state 주문 대기 상품 상태 (WAIT or ORDER)
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "delete from OrderGoods og where og.member.id = :memberPkId and og.state = :state")
    void deleteByMemberId(@Param("memberPkId") String memberPkId, @Param("state") String state);

    /**
     * 상품 아이디와 주문 대기 상품 상태로 상품 조회
     * @param goodsId 상품 아이디
     * @param state 주문 대기 상품 상태 (WAIT or ORDER)
     * @return 주문 대기 상품
     */
    @Query("select og from OrderGoods og where og.goods.id = :goodsId and og.state = :state and og.member.id = :memberId")
    OrderGoods findByGoodsId(@Param("goodsId") Long goodsId, @Param("state") String state, @Param("memberId") Long memberId);
}
