package book.yes48.service;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.web.form.goods.GoodsDto;
import book.yes48.web.form.goods.GoodsSearch;
import book.yes48.repository.goods.GoodsRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional(readOnly = true)
class GoodsServiceTest {

    @Autowired GoodsService goodsService;
    @Autowired GoodsRepository goodsRepository;
    @Autowired EntityManager em;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;


    @Before("")
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void clear() {
        em.clear();
    }

    // 상품 등록
    @BeforeEach
    public void sampleGoods() {
        // 국내 도서 4권
        for (int i = 0; i < 4; i++) {
            FileStore file = getFile();

            Goods domesticBook = Goods.builder()
                    .name("국내 책 " + i)
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

            goodsRepository.save(domesticBook);
        }

        // 외국 도서 4권
        for (int i = 0; i < 4; i++) {
            FileStore file = getFile();

            Goods foreignBook = Goods.builder()
                    .name("외국 책 " + i)
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

            goodsRepository.save(foreignBook);
        }

        // 음반 4장
        for (int i = 0; i < 4; i++) {
            FileStore file = getFile();

            Goods music = Goods.builder()
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

            goodsRepository.save(music);
        }
    }

    @Test
    @DisplayName("국내 도서 리스트")
    public void domesticBooksFindList() {
        // given
        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("국내 도서");

        PageRequest pageRequest = PageRequest.of(0, 4);

        // when
        Page<GoodsDto> domesticBook = goodsService.domesticBooksFindList(goodsSearch, pageRequest);

        // then
        assertThat(domesticBook.getSize()).isEqualTo(4);
        assertThat(domesticBook.getContent().get(0).getName()).contains("국내");
        assertThat(domesticBook.getContent().get(3).getName()).contains("국내");
    }

    @Test
    @DisplayName("외국 도서 리스트")
    public void foreignBooksFindList() {
        // given
        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("외국 도서");

        PageRequest pageRequest = PageRequest.of(0, 4);

        // when
        Page<GoodsDto> domesticBook = goodsService.foreignBooksFindList(goodsSearch, pageRequest);

        // then
        assertThat(domesticBook.getSize()).isEqualTo(4);
        assertThat(domesticBook.getContent().get(0).getName()).contains("외국");
        assertThat(domesticBook.getContent().get(3).getName()).contains("외국");
    }

    @Test
    @DisplayName("음반 리스트")
    public void musicFindList() {
        // given
        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort("외국 도서");

        PageRequest pageRequest = PageRequest.of(0, 4);

        // when
        Page<GoodsDto> domesticBook = goodsService.musicFindList(goodsSearch, pageRequest);

        // then
        assertThat(domesticBook.getSize()).isEqualTo(4);
        assertThat(domesticBook.getContent().get(0).getName()).contains("음반");
        assertThat(domesticBook.getContent().get(3).getName()).contains("음반");
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