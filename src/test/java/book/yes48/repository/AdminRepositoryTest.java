package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.admin.AdminGoodsDto;
import book.yes48.form.admin.AdminGoodsSaveForm;
import book.yes48.form.admin.search.AdminGoodsSearch;
import book.yes48.repository.admin.AdminRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static book.yes48.form.admin.search.SearchType.SORT;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    EntityManager em;

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