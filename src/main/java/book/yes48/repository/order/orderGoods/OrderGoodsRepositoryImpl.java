package book.yes48.repository.order.orderGoods;

import book.yes48.entity.goods.QGoods;
import book.yes48.entity.member.QMember;
import book.yes48.entity.order.OrderGoods;
import book.yes48.entity.order.QDelivery;
import book.yes48.entity.order.QOrder;
import book.yes48.entity.order.QOrderGoods;
import book.yes48.web.form.OrderHistoryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static book.yes48.entity.goods.QGoods.*;
import static book.yes48.entity.member.QMember.*;
import static book.yes48.entity.order.QDelivery.*;
import static book.yes48.entity.order.QOrder.*;
import static book.yes48.entity.order.QOrderGoods.*;

public class OrderGoodsRepositoryImpl implements OrderGoodsRepositoryCustom {

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public Page<OrderHistoryDto> findOrderList(Pageable pageable,  Long memberPkId) {
        List<OrderHistoryDto> result = queryFactory
                .select(Projections.constructor(OrderHistoryDto.class,
                        order.orderDate, order.id, orderGoods.member.name, orderGoods.goods.name, orderGoods.quantity))
                .from(orderGoods)
                .join(orderGoods.order, order)
                .join(orderGoods.goods, goods)
                .join(orderGoods.member, member)
                .where(orderGoods.state.eq("ORDER").and(orderGoods.member.id.eq(memberPkId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderGoods.id.desc())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(orderGoods.count())
                .from(orderGoods)
                .join(orderGoods.order, order)
                .join(orderGoods.goods, goods)
                .join(orderGoods.member, member)
                .where(orderGoods.state.eq("ORDER").and(orderGoods.member.id.eq(memberPkId)));

        return PageableExecutionUtils.getPage(result, pageable, count::fetchOne);
    }
}
