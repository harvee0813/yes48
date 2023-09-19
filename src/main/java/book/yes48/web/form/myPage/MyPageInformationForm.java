package book.yes48.web.form.myPage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageInformationForm {

    String userId;
    String name;
    String email;
    String basicAddress;
    String phone;

    @Builder
    public MyPageInformationForm(String userId, String name, String email, String basicAddress, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.basicAddress = basicAddress;
        this.phone = phone;
    }
}
