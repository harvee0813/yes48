package Book.yes48.Entity.goods.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AdminGoodsSearchDto {

    private String searchBy;
    private String searchQuery = "";
}
