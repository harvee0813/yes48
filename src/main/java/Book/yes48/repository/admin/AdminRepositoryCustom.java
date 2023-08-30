package Book.yes48.repository.admin;

import Book.yes48.Entity.goods.Goods;
import Book.yes48.form.admin.AdminGoodsDto;
import Book.yes48.form.admin.search.AdminGoodsSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRepositoryCustom {
    AdminGoodsDto getId(Long id);
    List<Goods> findByName(String name);
    List<AdminGoodsDto> getAll();
    Page<Goods> findAllPageAndSearch(Pageable pageable, AdminGoodsSearch adminGoodsSearch);
}
