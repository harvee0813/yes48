package book.yes48.service;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.web.form.NavbarDto;
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
@Transactional(readOnly = true)
class NavbarServiceTest {

    @Autowired
    NavbarService navbarService;
    @Autowired
    EntityManager em;

    @AfterEach
    public void clear() {
        em.clear();
    }

    @Test
    @DisplayName("네비바 검색")
    public void 검색() {
        // given
        FileStore file = getFile();
        Goods goods = Goods.builder()
                .name("테스트 책")
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
        String search = "테스트";
        PageRequest pageRequest = PageRequest.of(0,1);
        Page<NavbarDto> result = navbarService.goodsSearchList(pageRequest, search);

        // then
        assertThat(result.getContent().get(0).getName()).isEqualTo(goods.getName());

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