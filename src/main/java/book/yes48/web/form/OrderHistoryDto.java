package book.yes48.web.form;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderHistoryDto {

    private LocalDateTime orderDate;
    private Long orderId;
    private String name;
    private String goodsName;
    private int quantity;

    public OrderHistoryDto(LocalDateTime orderDate, Long orderId, String name, String goodsName, int quantity) {
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.name = name;
        this.goodsName = goodsName;
        this.quantity = quantity;
    }
}
