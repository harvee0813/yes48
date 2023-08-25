package Book.yes48.repository.admin;

import Book.yes48.Entity.goods.Goods;
import Book.yes48.Entity.goods.form.AdminGoodsDto;
import Book.yes48.Entity.goods.form.AdminGoodsSearchDto;
import Book.yes48.Entity.goods.form.AdminSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRepositoryCustom {
    AdminGoodsDto getId(Long id);
    List<Goods> findByName(String name);
    List<AdminGoodsDto> getAll();
    Page<AdminGoodsDto> searchPageComplex(AdminGoodsSearchDto goodsSearchDto, AdminSearchCondition condition, Pageable pageable);
}
