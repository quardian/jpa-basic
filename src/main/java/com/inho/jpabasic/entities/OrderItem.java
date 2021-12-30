package com.inho.jpabasic.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    //@Column(name="order_id")
    //private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders order;

    //@Column(name="item_id")
    //private Long itemId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private int orderPrice;

    private int count;

    // 양방향 관계 - 편의 메소드
    private void changeOrder(Orders order)
    {
        if ( order != null ){
            List<OrderItem> orderItems = order.getOrderItems();
            if ( orderItems != null )
            {// 동일항목 없는 경우 추가
                Optional<OrderItem> first = orderItems.stream().filter(m -> m == this).findFirst();
                if ( first.isEmpty()) orderItems.add(this);
            }
        }
        this.order = order;
    }

    public OrderItem(){}

    public OrderItem(Orders order, Item item, int orderPrice, int count) {
        //this.order = order;
        this.changeOrder(order);
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", item=" + item +
                ", orderPrice=" + orderPrice +
                ", count=" + count +
                '}';
    }
}
