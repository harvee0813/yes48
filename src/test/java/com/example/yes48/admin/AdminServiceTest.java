package com.example.yes48.admin;

import com.example.yes48.Service.AdminService;
import com.example.yes48.Entity.FileStore;
import com.example.yes48.Entity.goods.Goods;
import com.example.yes48.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminService adminService;

    @Test
    @DisplayName("중복된 이름을 가진 상품은 등록 실패")
    public void 상품중복확인_예외() throws IOException {
        // given
        Goods goods1 = getGoods();
        Goods goods2 = getGoods();

        // when
        adminService.saveGoods(goods1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.saveGoods(goods2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 상품입니다.");

    }

    // 테스트용 file
    private static FileStore getFile() {
        FileStore fileStore = new FileStore();
        fileStore.setFilepath("스프링부트와 AWS");
        fileStore.setFilepath("/files/" + UUID.randomUUID());

        return fileStore;
    }

    // 테스트용 상품
    private static Goods getGoods() {
        FileStore file = getFile();

        Goods goods = Goods.builder()
                .name("스프링 부트와 AWS로 혼자 구현하는 웹 서비스")
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