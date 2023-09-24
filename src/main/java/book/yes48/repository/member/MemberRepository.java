package book.yes48.repository.member;

import book.yes48.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * userId로 회원 조회
     * @param userId 유저 아이디
     * @return
     */
    @Query("select m from Member m where m.userId = :userId")
    Member findUser(@Param("userId") String userId);

}
