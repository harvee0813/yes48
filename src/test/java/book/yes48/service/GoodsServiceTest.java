package book.yes48.service;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.goods.GoodsDto;
import book.yes48.form.goods.GoodsSearch;
import book.yes48.repository.goods.GoodsRepository;
import jakarta.persistence.EntityManager;
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
class GoodsServiceTest {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("국내 도서 페이징 확인")
    public void 국내도서_페이징() {
        // given
        for (int i = 0; i < 2; i++) {
            FileStore file = getFile();

            Goods goods = Goods.builder()
                    .name("스프링 부트 " + i)
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

        PageRequest pageRequest = PageRequest.of(1, 5);

        // when
        Page<GoodsDto> result = goodsRepository.findAllBooks(goodsSearch, pageRequest);

        // then
        assertThat(result.getSize()).isEqualTo(5);
        assertThat(result.getContent()).extracting("name").containsExactly("스프링 부트");
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