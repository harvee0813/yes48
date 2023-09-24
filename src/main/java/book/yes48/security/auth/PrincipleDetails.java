package book.yes48.security.auth;

import book.yes48.entity.member.Member;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Getter
public class PrincipleDetails extends Member implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    /**
     * 일반 로그인
     */
    public PrincipleDetails(Member member) {
        this.member = member;
    }

    /**
     * OAuth 로그인
     */
    public PrincipleDetails(Member member, Map<String, Object> attributes) {
        this.member= member;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().toString()));

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

    @Override
    public boolean isAccountNonExpired() {
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

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
