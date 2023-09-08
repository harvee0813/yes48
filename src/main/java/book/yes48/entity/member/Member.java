package book.yes48.entity.member;

import book.yes48.entity.order.Order;
import book.yes48.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Member(String userId, String password, String name, String email, String phone, String postcode, String address, String state,
                  String detailsAddress, String extraAddress, Role role) {
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
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
