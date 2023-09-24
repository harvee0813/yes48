package book.yes48.repository.login;

import book.yes48.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Member, Long> {

    /**
     * 이름과 이메일로 멤버 조회
     * @param name 이름
     * @param email 이메일
     * @param state 상태 (N or Y)
     * @return
     */
    @Query("select m from Member m where m.name = :name and m.email = :email and m.state = :state")
    Member findMember(@Param("name") String name, @Param("email") String email, @Param("state") String state);

    /**
     * 이름과 핸드폰 번호로 멤버 조회
     * @param name 이름
     * @param phone 핸드폰 번호
     * @param state 상태 (N or Y)
     * @return
     */
    @Query("select m from Member m where m.name = :name and m.phone = :phone and m.state = :state")
    Member findMemberByPhone(@Param("name") String name, @Param("phone") String phone, @Param("state") String state);

    /**
     * 이름과 유저 아이디 확인
     * @param name 이름
     * @param userId 유저 아이디
     * @param state 상태 (N or Y)
     * @return
     */
    @Query("select m from Member m where m.name = :name and m.userId = :userId and m.state = :state")
    Member checkNameAndUserId(@Param("name") String name, @Param("userId") String userId, @Param("state") String state);

    /**
     * 아이디로 회원 조회
     * @param userId 유저 아이디
     * @return
     */
    @Query("select m from Member m where m.userId = :userId")
    Member findMemberById(@Param("userId") String userId);
}
