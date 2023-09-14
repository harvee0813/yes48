package book.yes48.web.form.myPage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageInformationForm {

    String userId;
    String name;
    String email;
    String address;
    String phone;

    @Builder
    public MyPageInformationForm(String userId, String name, String email, String address, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
}
