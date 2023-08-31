package book.yes48.form.admin.search;

import lombok.Getter;

public enum SearchType {
    SORT("상품 종류"),
    NAME("상품 이름"),
    STATE("상품 상태");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }

    public String description() { return description; }
}
