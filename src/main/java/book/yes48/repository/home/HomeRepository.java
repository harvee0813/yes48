package book.yes48.repository.home;

import book.yes48.entity.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Goods, Long>, HomeRepositoryCustom {
}
