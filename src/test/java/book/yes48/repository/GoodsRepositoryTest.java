package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.web.form.goods.GoodsDetailDto;
import book.yes48.web.form.goods.GoodsDto;
import book.yes48.web.form.goods.GoodsSearch;
import book.yes48.repository.goods.GoodsRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class GoodsRepositoryTest {

    @Autowired GoodsRepository goodsRepository;
    @Autowired EntityManager em;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before("스프링 시큐리티")
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void clean() {
        em.clear();
    }

    @Test
    @DisplayName("국내도서 페이징 확인")
    public void findAllBooksWithDomesticBook() {
        // given
        String sort = "국내 도서";
        persistGoods(sort);

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("국내 도서");   // sort 조건

        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        Page<GoodsDto> results = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results.getContent().get(0).getName()).isEqualTo("테스트 책 1");
        assertThat(results.getContent().get(1).getName()).isEqualTo("테스트 책 0");
    }

    @Test
    @DisplayName("외국도서 페이징 확인")
    public void findAllBooksWithForeignBook() {
        // given
        String sort = "외국 도서";
        persistGoods(sort);

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("외국 도서");   // sort 조건

        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        Page<GoodsDto> results = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results.getContent().get(0).getName()).isEqualTo("테스트 책 1");
        assertThat(results.getContent().get(1).getName()).isEqualTo("테스트 책 0");
    }

    @Test
    @DisplayName("음반 페이징 확인")
    public void findAllBooksWithMusic() {
        // given
        String sort = "음반";
        persistGoods(sort);

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("음반");  // sort 조건

        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        Page<GoodsDto> results = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results.getContent().get(0).getName()).isEqualTo("테스트 책 1");
        assertThat(results.getContent().get(1).getName()).isEqualTo("테스트 책 0");
    }

    @Test
    @DisplayName("아이디로 조회")
    public void getId() {
        // given
        FileStore file = getFile();

        Goods goods = Goods.builder()
                .name("스프링 부트")
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

        // when
        Goods saveGoods = goodsRepository.save(goods);
        GoodsDetailDto findGoods = goodsRepository.getId(saveGoods.getId());

        // then
        assertThat(findGoods.getId()).isEqualTo(goods.getId());
    }

    // 테스트용 file
    private static FileStore getFile() {
        FileStore fileStore = FileStore.builder()
                .filename("스프링부트와 AWS")
                .filepath("/files/" + UUID.randomUUID())
                .build();

        return fileStore;
    }

    // 테스트용 상품 - 국내도서, 외국도서, 음반 별로 등록되는 sort가 다르다.
    private void persistGoods(String sort) {
        for (int i = 0; i < 2; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("테스트 책 " + i)
                    .sort(sort)
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
    }
}