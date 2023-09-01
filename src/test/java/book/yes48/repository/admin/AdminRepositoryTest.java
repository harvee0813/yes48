package book.yes48.repository.admin;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.admin.AdminGoodsDto;
import book.yes48.form.admin.search.AdminGoodsSearch;
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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    public void clearTest() {
        em.clear();
    }

    @Test
    @DisplayName("관리자 상품 등록 테스트")
    public void 상품등록() {
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
    @DisplayName("페이징 확인 테스트")
    public void searchWithPage() {
        // given
        Goods goods1 = getGoodsOne();
        Goods goods2 = getGoodsTwo();

        em.persist(goods1);
        em.persist(goods2);

        AdminGoodsSearch search = new AdminGoodsSearch();
        PageRequest pageRequest = PageRequest.of(1, 12);

        // when
        Page<AdminGoodsDto> results = adminRepository.findAllPageAndSearch(pageRequest, search);

        // then
        assertThat(results.getSize()).isEqualTo(12);
        assertThat(results.getContent()).extracting("id").hasSize(12);
    }

    // 테스트용 file
    private static FileStore getFile() {
        FileStore fileStore = FileStore.builder()
                .filename("스프링부트와 AWS")
                .filepath("/files/" + UUID.randomUUID())
                .build();

        return fileStore;
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

    public static Goods getGoodsTwo() {
        FileStore file = getFile();

        Goods goods = Goods.builder()
                .name("스프링")
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
}