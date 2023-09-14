package book.yes48.entity.member;

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
    private String postcode;
    private String address;
    private String detailsAddress;
    private String extraAddress;
    private String state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    private String provider;
    private String providerId;

    @Builder
    public Member(String userId, String password, String name, String email, String phone, String postcode, String address, String state,
                  String detailsAddress, String extraAddress, Role role, String provider, String providerId) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.postcode = postcode;
        this.address = address;
        this.detailsAddress = detailsAddress;
        this.extraAddress = extraAddress;
        this.state = state;
        this.role = role;
        this.provider = provider;
        this.providerId =providerId;
    }

    // 비밀번호 변경
    public void changePassword(String password) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        this.password = bCryptPasswordEncoder.encode(password);
    }

    // 배송 주소 엡테이트
    public void updateAddress(AddressUpdateForm addressUpdateForm) {
        this.postcode = addressUpdateForm.getPostcode();
        this.address = addressUpdateForm.getAddress();
        this.detailsAddress = addressUpdateForm.getDetailsAddress();
        this.extraAddress = addressUpdateForm.getExtraAddress();
    }

    // 핸드폰 번호 업데이트
    public void updatePhone(PhoneUpdateForm phoneUpdateForm) {
        this.phone = phoneUpdateForm.getPhone();
    }

    // 배송 주소와 핸드폰 번호 동시 업데이트
    public void updateAddressAndPhone(AddressUpdateForm addressUpdateForm, PhoneUpdateForm phoneUpdateForm) {
        this.postcode = addressUpdateForm.getPostcode();
        this.address = addressUpdateForm.getAddress();
        this.detailsAddress = addressUpdateForm.getDetailsAddress();
        this.extraAddress = addressUpdateForm.getExtraAddress();
        this.phone = phoneUpdateForm.getPhone();
    }

    // 회원 탈퇴 -> 상태 변경
    public void withdraw(String state) {
        this.state = state;
    }
}
