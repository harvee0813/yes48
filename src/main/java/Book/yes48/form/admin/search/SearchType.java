package Book.yes48.form.admin.search;

import lombok.Getter;

public enum SearchType {
    sort("상품 종류"),
    name("상품 이름"),
    state("상품 상태");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
