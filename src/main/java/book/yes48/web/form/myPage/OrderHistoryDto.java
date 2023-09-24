package book.yes48.web.form.myPage;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 마이페이지 주문 조회에 사용
 */
@Getter
public class OrderHistoryDto {

    private LocalDateTime orderDate;
    private Long orderId;
    private String name;
    private String goodsName;
    private int quantity;
    private String address;

    public OrderHistoryDto(LocalDateTime orderDate, Long orderId, String name, String goodsName, int quantity, String address) {
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.name = name;
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.address = address;
    }
}
