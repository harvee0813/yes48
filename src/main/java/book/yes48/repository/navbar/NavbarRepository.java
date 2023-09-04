package book.yes48.repository.navbar;

import book.yes48.entity.goods.Goods;
import book.yes48.form.NavbarDto;
import book.yes48.form.goods.GoodsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavbarRepository extends JpaRepository<Goods, Long>, NavbarRepositoryCustom {
}
