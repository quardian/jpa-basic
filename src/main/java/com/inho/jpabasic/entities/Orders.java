package com.inho.jpabasic.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="order_id")
    private Long id;

    //@Column(name="member_id")
    //private Long memberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(fetch =FetchType.LAZY, mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Orders(){}

    public Orders(Member member, LocalDateTime orderDateTime, OrderStatus status) {
        this.member = member;
        this.orderDateTime = orderDateTime;
        this.status = status;
    }
}
