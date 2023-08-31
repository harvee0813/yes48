package book.yes48.repository.admin;

import book.yes48.entity.goods.Goods;
import book.yes48.form.admin.AdminGoodsDto;
import book.yes48.form.admin.search.AdminGoodsSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRepositoryCustom {
    AdminGoodsDto getId(Long id);
    List<Goods> findByName(String name);
    List<AdminGoodsDto> getAll();
    Page<AdminGoodsDto> findAllPageAndSearch(Pageable pageable, AdminGoodsSearch adminGoodsSearch);
}
