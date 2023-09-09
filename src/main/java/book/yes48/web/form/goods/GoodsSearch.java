package book.yes48.web.form.goods;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class GoodsSearch {

    private String sort;

    public boolean isEmpty() {
        return sort == null;
    }
}
