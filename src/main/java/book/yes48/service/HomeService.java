package book.yes48.service;

import book.yes48.form.goods.GoodsDto;
import book.yes48.repository.home.HomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class HomeService {

    @Autowired
    HomeRepository homeRepository;

    /**
     * 편집장의 추천 도서 4권 수정일 기준 desc 선별
     */
    public List<GoodsDto> selectFourSuggestionBooks() {
        List<GoodsDto> selectFourSuggestionBook = homeRepository.selectFourSuggestionBooks();

        return selectFourSuggestionBook;
    }

    /**
     * 최신 도서 4권 id desc 기준으로 선별
     */
    public List<GoodsDto> selectFourNewBooks() {
        List<GoodsDto> selectFourNewBooks = homeRepository.selectFourNewBooks();

        return selectFourNewBooks;
    }

    /**
     * 최신 음반 3권 id desc 기준으로 선별
     */
    public List<GoodsDto> selectThreeMusic() {
        List<GoodsDto> selectThreeMusic = homeRepository.selectThreeMusic();

        return selectThreeMusic;
    }
}
