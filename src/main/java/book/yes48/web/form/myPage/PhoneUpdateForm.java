package book.yes48.web.form.myPage;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PhoneUpdateForm {

    String phone;

    @Builder
    public PhoneUpdateForm(String phone) {
        this.phone = phone;
    }
}
