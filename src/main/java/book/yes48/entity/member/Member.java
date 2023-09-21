package book.yes48.entity.member;

import book.yes48.entity.cart.MyCart;
import book.yes48.entity.order.Order;
import book.yes48.entity.BaseTimeEntity;
import book.yes48.web.form.myPage.AddressUpdateForm;
import book.yes48.web.form.myPage.PhoneUpdateForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String state;
    private String postcode;
    private String basicAddress;
    private String detailsAddress;
    private String extraAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    private String provider;
    private String providerId;

    @Builder
    public Member(String userId, String password, String name, String email, String phone, Role role,
                  String provider, String postcode, String basicAddress, String detailsAddress, String extraAddress, String state, String providerId) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.basicAddress = basicAddress;
        this.postcode = postcode;
        this.detailsAddress = detailsAddress;
        this.extraAddress = extraAddress;
        this.state = state;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    // 비밀번호 변경
    public void changePassword(String password) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        this.password = bCryptPasswordEncoder.encode(password);
    }

    // 배송 주소 엡테이트
    public void updateAddress(AddressUpdateForm form) {
        this.basicAddress= form.getBasicAddress();
        this.postcode = form.getPostcode();
        this.detailsAddress = form.getDetailsAddress();
        this.extraAddress = form.getExtraAddress();
    }

    // 핸드폰 번호 업데이트
    public void updatePhone(PhoneUpdateForm phoneUpdateForm) {
        this.phone = phoneUpdateForm.getPhone();
    }

    // 배송 주소와 핸드폰 번호 동시 업데이트
    public void updateAddressAndPhone(AddressUpdateForm addressFrom, PhoneUpdateForm phoneForm) {
        this.basicAddress= addressFrom.getBasicAddress();
        this.postcode = addressFrom.getPostcode();
        this.detailsAddress = addressFrom.getDetailsAddress();
        this.extraAddress = addressFrom.getExtraAddress();
        this.phone = phoneForm.getPhone();
    }

    // 회원 탈퇴 -> 상태 변경
    public void withdraw(String state) {
        this.state = state;
    }
}
