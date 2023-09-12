package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.web.form.admin.AdminGoodsDto;
import book.yes48.web.form.admin.search.AdminGoodsSearch;
import book.yes48.repository.admin.AdminRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static book.yes48.web.form.admin.search.SearchType.SORT;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AdminRepositoryTest {
    
    @Autowired AdminRepository adminRepository;
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
    public void clean() {
        em.clear();
    }

    // 테스트용 상품
    public static Goods getGoodsOne() {
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

        return goods;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("관리자 상품 등록 테스트")
    public void save() {
        // given
        Goods goods = getGoodsOne();

        // when
        Long savedId = adminRepository.save(goods).getId();
        AdminGoodsDto findGoods = adminRepository.getId(savedId);

        // then
        assertThat(findGoods.getId()).isEqualTo(savedId);
        assertThat(findGoods.getName()).isEqualTo(goods.getName());
        assertThat(findGoods.getSort()).isEqualTo(goods.getSort());
        assertThat(findGoods.getAuthor()).isEqualTo(goods.getAuthor());
        assertThat(findGoods.getPublisher()).isEqualTo(goods.getPublisher());
        assertThat(findGoods.getPublisherDate()).isEqualTo(goods.getPublisherDate());
        assertThat(findGoods.getPrice()).isEqualTo(goods.getPrice());
        assertThat(findGoods.getStockQuantity()).isEqualTo(goods.getStockQuantity());
        assertThat(findGoods.getEvent()).isEqualTo(goods.getEvent());
        assertThat(findGoods.getState()).isEqualTo(goods.getState());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("페이징 확인 테스트 - 상품 종류 : '음반'")
    public void findAllPageAndSearch() {
        // given
        for (int i = 0; i < 2; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("음반입니다 " + i)
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

        AdminGoodsSearch search = new AdminGoodsSearch();
        search.setSearchBy("음반");
        search.setSearchType(SORT);
        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        Page<AdminGoodsDto> results = adminRepository.findAllPageAndSearch(pageRequest, search);

        // then
        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results.getContent().get(0).getSort()).isEqualTo("음반");
        assertThat(results.getContent().get(1).getSort()).isEqualTo("음반");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
        Goods saveGoods = adminRepository.save(goods);
        AdminGoodsDto findGoods = adminRepository.getId(saveGoods.getId());

        // then
        assertThat(findGoods.getName()).isEqualTo(goods.getName());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("상품 이름 조회")
    public void findByName() {
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
        String name = "스프링 부트";
        List<Goods> byName = adminRepository.findByName(name);

        // then
        assertThat(byName.get(0).getName()).isEqualTo(name);
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