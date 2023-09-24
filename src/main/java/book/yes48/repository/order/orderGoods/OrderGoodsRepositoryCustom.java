package book.yes48.repository.order.orderGoods;

import book.yes48.web.form.myPage.OrderHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderGoodsRepositoryCustom {
    Page<OrderHistoryDto> findOrderList(Pageable pageable, Long memberPkId);
}
