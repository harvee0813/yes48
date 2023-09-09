package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.web.form.NavbarDto;
import book.yes48.repository.navbar.NavbarRepository;
import book.yes48.service.NavbarService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
@ActiveProfiles("test")
public class NavbarRepositoryTest {

    @Autowired
    NavbarRepository navbarRepository;
    @Autowired
    NavbarService navbarService;
    @Autowired
    EntityManager em;

    @AfterEach
    void clean() {
        em.clear();
    }

    @Test
    @DisplayName("네비바 검색창 검색 결과와 페이징")
    public void goodsSearchList() {
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

        String search = "음반입니다";
        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        Page<NavbarDto> results = navbarService.goodsSearchList(pageRequest, search);

        // then
        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results.getContent().get(0).getName()).isEqualTo("음반입니다 1");
        assertThat(results.getContent().get(1).getName()).isEqualTo("음반입니다 0");
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
