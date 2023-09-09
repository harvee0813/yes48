package book.yes48.security;

import book.yes48.entity.member.Member;
import book.yes48.repository.login.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class LoginMemberDetails extends Member implements UserDetails {

    @Autowired
    private final LoginRepository loginRepository;
    @Autowired
    private Member member;

    public LoginMemberDetails(LoginRepository loginRepository, Member member) {
        this.loginRepository = loginRepository;
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().toString()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    /**
     * 계정 만료 여부
     * 계정 상태가 Y면 true, N이면 false
     */
    @Override
    public boolean isAccountNonExpired() {
//        Member findMember = loginRepository.findMemberById(member.getUserId());
//        if ("Y" == findMember.getState()) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
