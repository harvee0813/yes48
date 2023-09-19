package book.yes48.repository.myCart;

import book.yes48.entity.cart.MyCart;
import book.yes48.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyCartRepository extends JpaRepository<MyCart, Long> {

    @Query("select mc from MyCart mc where mc.member = :member")
    MyCart findMyCart(@Param("member") Member findMember);

    @Query("select mc from MyCart mc where mc.member.userId = :userId")
    MyCart findMyCartById(@Param("userId") String userId);
}
