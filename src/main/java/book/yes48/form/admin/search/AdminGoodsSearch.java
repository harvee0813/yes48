package book.yes48.form.admin.search;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminGoodsSearch {

    private String searchBy;
    private SearchType searchType;

    public boolean isEmpty() {
        return searchType == null || searchBy == null;
    }
}
