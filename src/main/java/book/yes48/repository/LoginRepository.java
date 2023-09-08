package book.yes48.repository;

import book.yes48.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.name = :name and m.email = :email and m.state = :state")
    Member findUser(@Param("name") String name, @Param("email") String email, @Param("state") String state);

    @Query("select m from Member m where m.name = :name and m.phone = :phone and m.state = :state")
    Member findUserByPhone(@Param("name") String name, @Param("phone") String phone, @Param("state") String state);

    @Query("select m from Member m where m.name = :name and m.userId = :userId and m.state = :state")
    Member checkNameAndUserId(@Param("name") String name, @Param("userId") String userId, @Param("state") String state);

    @Query("select m from Member m where m.userId = :userId")
    Member finByMember(@Param("userId") String userId);
}
