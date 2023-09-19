package book.yes48.repository.myPage;

import book.yes48.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPageRepository extends JpaRepository<Member, Long>, MyPageRepositoryCustom {

    @Query("select m from Member m where m.userId = :userId")
    Member findUser(@Param("userId") String userId);
}
