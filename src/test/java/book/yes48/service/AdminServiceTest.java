package book.yes48.service;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.form.admin.AdminGoodsUpdateForm;
import book.yes48.repository.admin.AdminRepository;
import book.yes48.repository.fileStore.FileRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AdminServiceTest {

    @Autowired
    AdminService adminService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    void clean() {
        adminRepository.deleteAll();
        fileRepository.deleteAll();
        em.clear();
    }

    @Test
    @DisplayName("상품 이름 중복 검증 테스트")
    public void 상품이름중복예외() {
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
}