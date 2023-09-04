package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.goods.GoodsDto;
import book.yes48.form.goods.GoodsSearch;
import book.yes48.repository.goods.GoodsRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    void tearDown() {
        goodsRepository.deleteAll();
    }

    @Test
    @DisplayName("국내도서 페이징 확인")
    public void 국내도서_페이징() {
        // given
        for (int i = 0; i < 2; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("국내 도서 " + i)
                    .sort("국내 도서")
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

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("국내 도서");

        PageRequest pageRequest = PageRequest.of(1, 8);

        // when
        Page<GoodsDto> results = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(8);
    }

    @Test
    @DisplayName("외국도서 페이징 확인")
    public void 외국도서_페이징() {
        // given
        for (int i = 0; i < 2; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("외국 도서 " + i)
                    .sort("외국 도서")
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

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("외국 도서");

        PageRequest pageRequest = PageRequest.of(1, 8);

        // when
        Page<GoodsDto> results = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(8);
    }

    @Test
    @DisplayName("음반 페이징 확인")
    public void 음반_페이징() {
        // given
        for (int i = 0; i < 2; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("음반 " + i)
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

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("음반");

        PageRequest pageRequest = PageRequest.of(1, 8);

        // when
        Page<GoodsDto> results = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(8);
    }

    @Test
    @DisplayName("국내도서, 외국도서, 음반이 아닐 경우 where절 생략")
    public void 지정된_종류_X() {
        // given
        for (int i = 0; i < 2; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("음반 " + i)
                    .sort("테스트 종류")
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

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("테스트 종류");

        PageRequest pageRequest = PageRequest.of(1, 8);

        // when
        Page<GoodsDto> results = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(8);
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