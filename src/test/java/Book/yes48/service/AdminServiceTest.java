package Book.yes48.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminServiceTest {

//    @Autowired
//    AdminRepository adminRepository;
//    @Autowired
//    AdminService adminService;
//
//    @Test
//    @DisplayName("중복된 이름을 가진 상품은 등록 실패")
//    public void 상품중복확인_예외() throws IOException {
//        // given
//        Goods goods1 = getGoods();
//        Goods goods2 = getGoods();
//
//        // when
//        adminService.saveGoods(goods1);
//        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.saveGoods(goods2));
//
//        // then
//        assertThat(e.getMessage()).isEqualTo("이미 존재하는 상품입니다.");
//
//    }
//
//    // 테스트용 file
//    private static FileStore getFile() {
//        FileStore fileStore = FileStore.builder()
//                .filepath("/files/" + UUID.randomUUID())
//                .filename("스프링부트와 AWS").build();
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