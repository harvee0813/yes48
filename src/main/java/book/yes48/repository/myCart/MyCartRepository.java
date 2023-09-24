package book.yes48.repository.myCart;

import book.yes48.entity.cart.MyCart;
import book.yes48.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyCartRepository extends JpaRepository<MyCart, Long> {

    /**
     * member 고유의 장바구니 조회
     * @param findMember 장바구니 찾을 맴버
     * @return
     */
    @Query("select mc from MyCart mc where mc.member = :member")
    MyCart findMyCart(@Param("member") Member findMember);

    /**
     * 유저 아이디로 장바구니 조회
     * @param userId 유저 아이디
     * @return
     */
    @Query("select mc from MyCart mc where mc.member.userId = :userId")
    MyCart findMyCartById(@Param("userId") String userId);
}
