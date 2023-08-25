package Book.yes48.repository.admin;

import Book.yes48.Entity.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Goods, Long>, AdminRepositoryCustom {

}