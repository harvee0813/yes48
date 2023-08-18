package com.example.yes48.Service;

import com.example.yes48.domain.goods.Goods;
import com.example.yes48.domain.goods.GoodsSaveForm;
import com.example.yes48.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AdminService {

    @Autowired
    private AdminRepository goodsRepository;

    // 상품 등록
    @Transactional
    public void addGoods(GoodsSaveForm form, MultipartFile file) throws IOException {

        // 파일 경로 지정
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 파일 서버 명 지정
        String originalFilename = file.getOriginalFilename();
        String storeFileName = createdStoreFileName(originalFilename);

        File saveFile = new File(projectPath, storeFileName);
        file.transferTo(saveFile);

        Goods goods = Goods.builder()
                .name(form.getName())
                .sort(form.getSort())
                .filename(storeFileName)
                .filepath("/files/" + storeFileName)
                .author(form.getAuthor())
                .publisher(form.getPublisher())
                .publisherDate(form.getPublisherDate())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .event(form.getEvent())
                .state(form.getState())
                .build();

        goodsRepository.saveGoods(goods);
    }

    // 서버에 저장되는 파일명
    private String createdStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    // 파일 확장자명 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
