package book.yes48.service;

import book.yes48.web.form.NavbarDto;
import book.yes48.repository.navbar.NavbarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NavbarService {

    @Autowired
    private final NavbarRepository navbarRepository;

    public Page<NavbarDto> goodsSearchList(Pageable pageable, String search) {
        Page<NavbarDto> find = navbarRepository.goodsSearchList(pageable, search);

        return find;
    }
}
