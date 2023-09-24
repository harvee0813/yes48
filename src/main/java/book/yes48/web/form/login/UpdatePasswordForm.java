package book.yes48.web.form.login;

import lombok.Builder;
import lombok.Getter;

/**
 * 비밀번호 변경에 사용
 */
@Getter
public class UpdatePasswordForm {

    private String userId;
    private String password;

    @Builder
    public UpdatePasswordForm(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
