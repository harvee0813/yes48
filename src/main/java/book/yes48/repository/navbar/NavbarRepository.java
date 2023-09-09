package book.yes48.repository.navbar;

import book.yes48.entity.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavbarRepository extends JpaRepository<Goods, Long>, NavbarRepositoryCustom {
}
