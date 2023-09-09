package book.yes48.security;

import book.yes48.entity.member.Member;
import book.yes48.repository.login.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginMemberDetailsService implements UserDetailsService {

    @Autowired
    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username = {}", username);

        Member findMember = loginRepository.findMemberById(username);
        log.info("findMember.getUserId = {}", findMember.getUserId());
        log.info("findMember = {}", findMember);

        if (findMember != null) {
            return new LoginMemberDetails(loginRepository, findMember);
        } else {
            throw new UsernameNotFoundException("아이디와 비밀번호가 일치하지 않습니다.");
        }
    }
}
