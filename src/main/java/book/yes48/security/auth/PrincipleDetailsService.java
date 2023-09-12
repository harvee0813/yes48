package book.yes48.security.auth;

import book.yes48.entity.member.Member;
import book.yes48.repository.login.LoginRepository;
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

    // security session
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username = {}", username);

        Member findMember = loginRepository.findMemberById(username);
        log.info("findMember.getUserId = {}", findMember.getUserId());
        log.info("findMember = {}", findMember);

        if (findMember == null) {
            throw new UsernameNotFoundException("계정이 존재하지 않습니다.");
        } else if (findMember.getState().equals("N")) {
            throw new UsernameNotFoundException("탈퇴된 계정입니다.");
        } else {
            return new PrincipleDetails(findMember);
        }

    }
}
