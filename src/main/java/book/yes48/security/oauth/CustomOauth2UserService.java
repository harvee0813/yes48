package book.yes48.security.oauth;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.member.MemberRepository;
import book.yes48.security.auth.PrincipleDetails;
import book.yes48.security.oauth.provider.GoogleUserInfo;
import book.yes48.security.oauth.provider.NaverUserInfo;
import book.yes48.security.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService  { //OAuth2UserService<OAuth2UserRequest, OAuth2User>
    
    @Autowired
    MemberRepository memberRepository;
    
    // 구글로 부터 받은 userRequest 데이터에 대한 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration" + userRequest.getClientRegistration());
        System.out.println("getAccessToken" + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("getAttributes" + oAuth2User.getAuthorities());

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));

        } else {

        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String userId = provider + "_" + providerId;
        String username = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();
        Role role = Role.USER;
        
        Member memberEntity = memberRepository.findUser(userId);
        
        if (memberEntity == null) {
            memberEntity = Member.builder()
                    .userId(userId)
                    .name(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .state("Y")
                    .build();
            
            memberRepository.save(memberEntity);
        }
        
        return new PrincipleDetails(memberEntity, oAuth2User.getAttributes());
    }
}
