package book.yes48.service;

import book.yes48.form.admin.AdminGoodsDto;
import book.yes48.form.admin.AdminGoodsSaveForm;
import book.yes48.form.admin.AdminGoodsUpdateForm;
import book.yes48.form.admin.search.AdminGoodsSearch;
import book.yes48.repository.fileStore.FileRepository;
import book.yes48.repository.admin.AdminRepository;
import book.yes48.entity.goods.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private FileRepository fileRepository;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public String illegalExHandler(IllegalArgumentException ex) {
        log.error("[exceptionHandler] ex", ex);
        return "error/500";
    }

    /**
     * 상품 등록
     */
    @Transactional
    public Long saveGoods(AdminGoodsSaveForm form, MultipartFile file) throws IOException {

        duplicationCheckGoodsName(form.getName());

        Goods save = Goods.builder()
                .name(form.getName())
                .sort(form.getSort())
                .author(form.getAuthor())
                .publisher(form.getPublisher())
                .publisherDate(form.getPublisherDate())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .event(form.getEvent())
                .state(form.getState())
                .fileStore(form.file(file))
                .build();

        adminRepository.save(save);

        return save.getId();
    }

    /**
     * 상품 수정
     * @param goodsId 수정할 상품에서 얻은 상품 id
     * @param form 상품 수정 전용 화면
     * @param file 변경된 이미지 (Empty 가능)
     */
    @Transactional
    public void updateGoods(Long goodsId, AdminGoodsUpdateForm form, MultipartFile file) throws IOException {
        Goods findGoods = adminRepository.findById(goodsId).orElseThrow(() -> new NoSuchElementException());
        log.info("Goods log={}", findGoods);

        if (file.isEmpty()) {
            updateGoods(form, findGoods);
        } else if (!file.isEmpty()) {
            fileRepository.deleteById(findGoods.getFileStore().getId()); // 기존 파일 삭제
            UpdateGoodsAndFile(form, file, findGoods);
        }
    }

    /**
     * 상품 목록 검색 및 페이지네이션
     * @param pageable 페이지네이션
     * @param adminGoodsSearch 검색 조건 및 내용
     */
    public Page<AdminGoodsDto> findList(Pageable pageable, AdminGoodsSearch adminGoodsSearch) {
        Page<AdminGoodsDto> find = adminRepository.findAllPageAndSearch(pageable, adminGoodsSearch);
        return find.map(AdminGoodsDto::new);
    }

    /**
     * 상품 이름 중복 검증
     * @param name 검증할 상품 이름
     */
    public String nameCheck(String name) {
        List<Goods> byGoodsName = adminRepository.findByName(name);
        if(!byGoodsName.isEmpty()) {
            return null;
        } else {
            return "ok";
        }
    }

    /**
     * 상품 상세 정보를 위한 아이디 가져오기
     * @param goodsId 상품 아이디
     */
    public AdminGoodsDto getId(Long goodsId) {
        AdminGoodsDto findGoods = adminRepository.getId(goodsId);
        return findGoods;
    }

    // 상품 업데이트 - file이 있을 때
    private static void UpdateGoodsAndFile(AdminGoodsUpdateForm form, MultipartFile file, Goods findGoods) throws IOException {
        findGoods.updateGoodsAndFile(
                form.getId(),
                form.getName(),
                form.getSort(),
                form.getAuthor(),
                form.getPublisher(),
                form.getPublisherDate(),
                form.getPrice(),
                form.getStockQuantity(),
                form.getEvent(),
                form.getState(),
                form.file(file));
    }

    // 상품 업데이트 - file이 비어있을 때
    private static void updateGoods(AdminGoodsUpdateForm form, Goods findGoods) {
        findGoods.updateGoods(
                form.getId(),
                form.getName(),
                form.getSort(),
                form.getAuthor(),
                form.getPublisher(),
                form.getPublisherDate(),
                form.getPrice(),
                form.getStockQuantity(),
                form.getEvent(),
                form.getState());
    }

    // 상품 이름 중복 검증 로직
    public void duplicationCheckGoodsName(String form) {
        List<Goods> findGoods = adminRepository.findByName(form);
        if (!findGoods.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 상품입니다.");
        }
    }
}
