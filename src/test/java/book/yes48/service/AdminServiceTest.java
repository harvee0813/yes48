package book.yes48.service;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.admin.AdminGoodsDto;
import book.yes48.form.admin.AdminGoodsSaveForm;
import book.yes48.form.admin.AdminGoodsUpdateForm;
import book.yes48.repository.admin.AdminRepository;
import book.yes48.repository.fileStore.FileRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AdminServiceTest {

    @Autowired
    AdminService adminService;
    @Autowired
    EntityManager em;

    @AfterEach
    void clean() {
        em.clear();
    }

    @Test
    @DisplayName("상품 등록")
    public void saveGoods() throws IOException {
        // given
        String filename = "filename";
        String filepath = "filepath";
        MockMultipartFile file = getMockMultipartFile(filename, filepath);

        AdminGoodsSaveForm form = AdminGoodsSaveForm.builder()
                .name("책 테스트")
                .sort("국내 도서")
                .author("저자")
                .publisher("출판사")
                .publisherDate("2023-09-09")
                .price(10000)
                .stockQuantity(100)
                .event("N")
                .state("Y")
                .build();

        // when
        Long goodsId = adminService.saveGoods(form, file);
        AdminGoodsDto findGoods = adminService.getId(goodsId);

        // then
        assertThat(form.getName()).isEqualTo(findGoods.getName());
    }

    @Test
    @DisplayName("상품 등록시 상품 이름 중복 검증 테스트")
    public void duplicationCheckGoodsName() {
        // given
        Goods goods1 = getGoodsOne();
        Goods goods2 = getGoodsOne();

        em.persist(goods1);

        // when, then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            adminService.duplicationCheckGoodsName(goods2.getName());
        }, "이미 존재하는 상품입니다.");
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

    // MultipartFile 테스트 파일
    private MockMultipartFile getMockMultipartFile(String filename, String filepath) throws IOException {
        return new MockMultipartFile(filename, filepath.getBytes());
    }
}