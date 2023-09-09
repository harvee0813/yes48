package book.yes48.repository.goods;

import book.yes48.web.form.goods.GoodsDetailDto;
import book.yes48.web.form.goods.GoodsDto;
import book.yes48.web.form.goods.GoodsSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsRepositoryCustom {

    Page<GoodsDto> findAllBooks(GoodsSearch goodsSearch, Pageable pageable);

    GoodsDetailDto getId(Long id);
}
