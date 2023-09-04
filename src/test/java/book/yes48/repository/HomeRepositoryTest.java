package book.yes48.repository;

import book.yes48.form.goods.GoodsDto;
import book.yes48.repository.home.HomeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional(readOnly = true)
public class HomeRepositoryTest {

    @Autowired
    HomeRepository homeRepository;

    @AfterEach
    void tearDown() {
        homeRepository.deleteAll();
    }

    @Test
    @DisplayName("메인페이지에 들어갈 편집장의 추천 4권 선별")
    public void 편집장의_추천도서_4권_선별() {
        // when
        List<GoodsDto> goodsDtos = homeRepository.selectFourSuggestionBooks();

        // then
        Assertions.assertThat(goodsDtos).hasSize(4);

    }

    @Test
    @DisplayName("메인페이지에 들어갈 최신 도서 4권 선별")
    public void 최신도서_4권_선별() {
        // when
        List<GoodsDto> goodsDtos = homeRepository.selectFourNewBooks();

        // then
        Assertions.assertThat(goodsDtos).hasSize(4);

    }

    @Test
    @DisplayName("메인페이지에 들어갈 최신 음반 3개 선별")
    public void 음반_3권_선별() {
        // when
        List<GoodsDto> goodsDtos = homeRepository.selectThreeMusic();

        // then
        Assertions.assertThat(goodsDtos).hasSize(3);

    }
}
