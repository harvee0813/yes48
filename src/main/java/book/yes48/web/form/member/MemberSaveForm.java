package book.yes48.web.form.member;

import book.yes48.entity.member.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 가입에 사용
 */
@Getter
public class MemberSaveForm {

    private Long id;

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    @NotBlank(message = "주소를 입력해주세요.")
    private String postcode;

    @NotBlank
    private String basicAddress;

    @NotBlank(message = "상세주소를 입력해주세요.")
    private String detailsAddress;

    private String extraAddress;
    private String state;
    private Role role;

    @Builder
    public MemberSaveForm(String userId, String password, String name, String email, String phone, String postcode, String basicAddress,
                          String detailsAddress, String state, Role role, String extraAddress) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.postcode = postcode;
        this.basicAddress = basicAddress;
        this.state = state;
        this.detailsAddress = detailsAddress;
        this.extraAddress = extraAddress;
        this.role = role;
    }
}
