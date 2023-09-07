package book.yes48.form.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIdDto {

    private String userId;

    public UserIdDto(String userId) {
        this.userId = userId;
    }
}
