package book.yes48.repository.admin;

import book.yes48.entity.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Goods, Long>, AdminRepositoryCustom {

}