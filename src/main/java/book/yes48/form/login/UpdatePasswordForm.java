package book.yes48.form.login;

import lombok.Builder;
import lombok.Getter;

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
