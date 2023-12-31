package book.yes48.repository.home;

import book.yes48.web.form.goods.GoodsDto;

import java.util.List;

public interface HomeRepositoryCustom {
    List<GoodsDto> selectFourSuggestionBooks();     // 편집자의 추천 도서 4권 선택
    List<GoodsDto> selectFourNewBooks();            // 최신 도서 4권 선택
    List<GoodsDto> selectThreeMusic();              // 최신 음반 3권 선택
}
