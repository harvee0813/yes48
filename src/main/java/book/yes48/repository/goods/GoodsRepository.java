package book.yes48.repository.goods;

import book.yes48.entity.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsRepositoryCustom {
}
