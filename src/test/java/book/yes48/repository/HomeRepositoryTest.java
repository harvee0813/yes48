package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.goods.GoodsDto;
import book.yes48.repository.home.HomeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional(readOnly = true)
@ActiveProfiles("test")
public class HomeRepositoryTest {

    @Autowired
    HomeRepository homeRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    void clean() {
        em.clear();
    }

    @Test
    @DisplayName("편집장의 추천 4권 선별")
    public void selectFourSuggestionBooks() {
        // given
        for (int i = 0; i < 5; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("추천도서입니다. " + i)
                    .sort("국내도서")
                    .author("이동욱")
                    .publisher("프리렉")
                    .publisherDate("20191129")
                    .price(22000)
                    .stockQuantity(20)
                    .fileStore(file)
                    .event("Y")
                    .state("Y")
                    .build();

            em.persist(goods);
        }

        // when
        List<GoodsDto> goodsDtos = homeRepository.selectFourSuggestionBooks();

        // then
        Assertions.assertThat(goodsDtos).hasSize(4);

    }

    @Test
    @DisplayName("메인페이지에 들어갈 최신 도서 4권 선별")
    public void selectFourNewBooks() {
        // given
        for (int i = 0; i < 5; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("최신 도서입니다. " + i)
                    .sort("최신 도서")
                    .author("이동욱")
                    .publisher("프리렉")
                    .publisherDate("20191129")
                    .price(22000)
                    .stockQuantity(20)
                    .fileStore(file)
                    .event("N")
                    .state("Y")
                    .build();

            em.persist(goods);
        }

        // when
        List<GoodsDto> goodsDtos = homeRepository.selectFourNewBooks();

        // then
        Assertions.assertThat(goodsDtos).hasSize(4);

    }

    @Test
    @DisplayName("메인페이지에 들어갈 최신 음반 3개 선별")
    public void selectThreeMusic() {
        // given
        for (int i = 0; i < 5; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("음반입니다. " + i)
                    .sort("음반")
                    .author("이동욱")
                    .publisher("프리렉")
                    .publisherDate("20191129")
                    .price(22000)
                    .stockQuantity(20)
                    .fileStore(file)
                    .event("N")
                    .state("Y")
                    .build();

            em.persist(goods);
        }

        // when
        List<GoodsDto> goodsDtos = homeRepository.selectThreeMusic();

        // then
        Assertions.assertThat(goodsDtos).hasSize(3);

    }

    // 테스트용 file
    private static FileStore getFile() {
        FileStore fileStore = FileStore.builder()
                .filename("스프링부트와 AWS")
                .filepath("/files/" + UUID.randomUUID())
                .build();

        return fileStore;
    }
}
