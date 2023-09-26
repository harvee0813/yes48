package book.yes48.security.auth;

import book.yes48.entity.cart.MyCart;
import book.yes48.entity.member.Member;
import book.yes48.repository.login.LoginRepository;
import book.yes48.repository.myCart.MyCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipleDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private final LoginRepository loginRepository;
    @Autowired
    private final MyCartRepository myCartRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 계정 확인
        Member findMember = loginRepository.findMemberById(username);

        if (findMember == null) {
            throw new UsernameNotFoundException("계정이 존재하지 않습니다.");
        } else if (findMember.getState().equals("N")) {
            throw new UsernameNotFoundException("탈퇴된 계정입니다.");
        } else {

            // 장바구니 생성
            MyCart findMyCart = myCartRepository.findMyCart(findMember);
            if (findMyCart == null) {
                MyCart myCart = MyCart.builder()
                        .member(findMember)
                        .build();

                myCartRepository.save(myCart);
            }

            return new PrincipleDetails(findMember);
        }

    }
}
