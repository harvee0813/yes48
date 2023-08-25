package Book.yes48.repository.admin;

import Book.yes48.Entity.FileStore;
import Book.yes48.Entity.goods.Goods;
import Book.yes48.Entity.goods.form.AdminGoodsDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class AdminRepositoryTest {

//    @Autowired
//    AdminRepository adminRepository;
//
//    @Test
//    public void 상품등록() {
//        // given
//        Goods goods = getGoods();
//
//        // when
//        Long savedId = adminRepository.save(goods).getId();
//        AdminGoodsDto findGoods = adminRepository.getId(savedId);
//
//        // then
//        Assertions.assertThat(findGoods.getId()).isEqualTo(savedId);
//        Assertions.assertThat(findGoods.getName()).isEqualTo(goods.getName());
//        Assertions.assertThat(findGoods.getSort()).isEqualTo(goods.getSort());
//        Assertions.assertThat(findGoods.getAuthor()).isEqualTo(goods.getAuthor());
//        Assertions.assertThat(findGoods.getPublisher()).isEqualTo(goods.getPublisher());
//        Assertions.assertThat(findGoods.getPublisherDate()).isEqualTo(goods.getPublisherDate());
//        Assertions.assertThat(findGoods.getPrice()).isEqualTo(goods.getPrice());
//        Assertions.assertThat(findGoods.getStockQuantity()).isEqualTo(goods.getStockQuantity());
//        Assertions.assertThat(findGoods.getEvent()).isEqualTo(goods.getEvent());
//        Assertions.assertThat(findGoods.getState()).isEqualTo(goods.getState());
//    }
//
//    // 테스트용 file
//    private static FileStore getFile() {
//        FileStore fileStore = FileStore.builder()
//                .filename("스프링부트와 AWS")
//                .filepath("/files/" + UUID.randomUUID())
//                .build();
//
//        return fileStore;
//    }
//
//    // 테스트용 상품
//    private static Goods getGoods() {
//        FileStore file = getFile();
//
//        Goods goods = Goods.builder()
//                .name("스프링 부트와 AWS로 혼자 구현하는 웹 서비스")
//                .sort("국내 도서")
//                .author("이동욱")
//                .publisher("프리렉")
//                .publisherDate("20191129")
//                .price(22000)
//                .stockQuantity(20)
//                .fileStore(file)
//                .event("N")
//                .state("Y")
//                .build();
//
//        return goods;
//    }
}