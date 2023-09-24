package book.yes48.web.form.myPage;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * 핸드폰 번호 업데이트에 사용
 */
@Getter
public class PhoneUpdateForm {

    String phone;

    @Builder
    public PhoneUpdateForm(String phone) {
        this.phone = phone;
    }
}
