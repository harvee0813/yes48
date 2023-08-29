package Book.yes48.service;

import Book.yes48.form.admin.AdminGoodsDto;
import Book.yes48.form.admin.AdminGoodsUpdateForm;
import Book.yes48.form.admin.search.AdminGoodsSearch;
import Book.yes48.form.admin.search.SearchType;
import Book.yes48.repository.admin.AdminRepository;
import Book.yes48.Entity.goods.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 상품 등록
     * @param goods 상품 domain
     */
    @Transactional
    public Long saveGoods(Goods goods) throws IOException {

        validateDuplicationGoods(goods);
        adminRepository.save(goods);

        return goods.getId();
    }

    @Transactional
    public void updateGoods(Long goodsId, AdminGoodsUpdateForm form, MultipartFile file) throws IOException {
        Goods findGoods = adminRepository.findById(goodsId).orElseThrow(
                () -> new NoSuchElementException());
        log.info("Goods log={}", findGoods);

        if (file.isEmpty()) {
            updateGoods(form, findGoods);
        } else if (!file.isEmpty()) {
            UpdateGoodsAndFile(form, file, findGoods);
        }
    }

    // 상품 업데이트 - fileStore 있을 때
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

    // 상품 업데이트 - fileStore 없을 때
    private static void updateGoods(AdminGoodsUpdateForm form, Goods findGoods) {
        findGoods.updateGoods(form.getId(),
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

    // 중복 상품 검증
    public void validateDuplicationGoods (Goods goods) {

        List<Goods> findGoods = adminRepository.findByName(goods.getName());
        if (!findGoods.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 상품입니다.");
        }
    }

    public Page<AdminGoodsDto> findList(Pageable pageable, AdminGoodsSearch adminGoodsSearch) {
        Page<Goods> find = adminRepository.findAllPageAndSearch(pageable, adminGoodsSearch);
        return find.map(AdminGoodsDto::new);
    }

}
