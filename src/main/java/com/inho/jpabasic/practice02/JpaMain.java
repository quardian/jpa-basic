package com.inho.jpabasic.practice02;

import com.inho.jpabasic.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        System.out.println(Charset.defaultCharset().displayName());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2Db");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{
            insert(em);
            find(em);

            tx.commit();
        }catch (Exception e) {
            if ( tx != null ) tx.rollback();
            e.printStackTrace();
        }
        finally {
            if ( em != null && em.isOpen() ) em.close();
            emf.close();
        }
    }


    public static void insert(EntityManager em)
    {
        Item itemA = new Item("itemA", 1000, 100);
        em.persist(itemA);

        Member memberA = new Member("memeberA", "seolul", "jong-ro", "152198");
        em.persist(memberA);

        Orders order = new Orders(memberA, LocalDateTime.now(), OrderStatus.ORDER);
        em.persist(order);

        OrderItem orderItem = new OrderItem(order, itemA, itemA.getPrice(), 1);
        order.getOrderItems().add(orderItem);

        em.persist(orderItem);
    }

    public static void find(EntityManager em)
    {
        em.flush();
        em.clear();

        Orders findOrder = em.find(Orders.class, 3L);
        System.out.println("findOrder = " + findOrder);

        Member findMember = findOrder.getMember();
        System.out.println("findMember = " + findMember);

        List<OrderItem> orderItems = findOrder.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            System.out.println("orderItem = " + orderItem);
            Item item = orderItem.getItem();
            System.out.println("item = " + item);
        }

    }
}
