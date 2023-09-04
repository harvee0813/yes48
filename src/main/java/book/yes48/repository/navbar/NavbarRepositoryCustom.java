package book.yes48.repository.navbar;

import book.yes48.form.NavbarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NavbarRepositoryCustom {
    Page<NavbarDto> goodsSearchList(Pageable pageable, String search);
}
