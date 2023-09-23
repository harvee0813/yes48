package book.yes48.repository.order.orderGoods;

import book.yes48.entity.order.OrderGoods;
import book.yes48.web.form.OrderHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderGoodsRepositoryCustom {
    Page<OrderHistoryDto> findOrderList(Pageable pageable, Long memberPkId);
}
