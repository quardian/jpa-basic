package com.inho.jpabasic;

import com.inho.jpabasic.entities.Member;

import javax.persistence.*;
import java.util.List;


public class JpaMain
{
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2Db");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{

            //insert(em);
            //find(em);
            //remove(em);
            //update(em);
            //jpql(em);
            //context(em);
            contextQuery(em);

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
        for(int i=10; i<100; i++){
            int no = i + 1;
            Member member = new Member( Long.valueOf(no), String.format("오징어%d", no) );
            em.persist(member);
        }
    }

    public static void find(EntityManager em)
    {
        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember = " + findMember);
    }

    public static void remove(EntityManager em)
    {
        Member findMember = em.find(Member.class, 2L);
        em.remove(findMember);
    }

    public static void update(EntityManager em)
    {
        Member findMember = em.find(Member.class, 1L);
        findMember.setName("이인호*");
    }


    public static void jpql(EntityManager em)
    {
        String qlString = "select m from Member as m";
        TypedQuery<Member> query = em.createQuery(qlString, Member.class);
        List<Member> list = query
                                .setFirstResult(10) /* 0 부터 시작 = (page -1) * pageSize */
                                .setMaxResults(10)  /* 조회 건수   = pageSize */
                                .getResultList();
        for (Member member : list) {
            System.out.println("member = " + member);
        }
    }


    public static void context(EntityManager em)
    {
        // 비영속성
        Member member = new Member(1000L, "영속성");

        // 영속성 상태
        System.out.println("==== BEFORE =====");
        // 1차 캐시 저장 및 "쓰기 지연 SQL 저장소"에 INSERT SQL 생성 저장.
        em.persist(member); // 영속상태 ( 이때 DB 저장 안됨 )
        System.out.println("==== AFTER  =====");

        // 1차 캐시의 내용 조회 ( SELECT 쿼리 실행 안함 )
        Member findMember = em.find(Member.class, 1000L);

        // 동일성 보장 ( 객체 주소 동일 : 1차 캐시가 있어 가능 )
        boolean identity = member == findMember;

        // 준영속 상태(detached)
        em.detach(member);

        // 삭제 상태
        em.remove(member);

    }

    public static void contextQuery(EntityManager em)
    {
        // 영속성 컨텍스트 관리
        System.out.println("==== BEFORE #1 =====");
        Member findMember = em.find(Member.class, 11L);
        System.out.println("==== AFTER  #1 =====");

        System.out.println("==== BEFORE  #1 AGAIN =====");
        Member findMember2 = em.find(Member.class, 11L);
        System.out.println("findMember2 = " + (findMember == findMember2) );
        System.out.println("==== AFTER  #1 AGAIN =====");


        // 영속성 컨텍스트에 등록되는가?
        String qlString = "select m from Member m";
        TypedQuery<Member> query = em.createQuery(qlString, Member.class);
        System.out.println("==== BEFORE #getResultList =====");
        List<Member> list = query.getResultList();
        System.out.println("==== AFTER  #getResultList =====");

        System.out.println("==== BEFORE #2 =====");
        findMember = em.find(Member.class, 11L);
        System.out.println("==== AFTER  #2 =====");

        System.out.println("==== BEFORE #3 =====");
        findMember = em.find(Member.class, 20L);
        System.out.println("==== AFTER  #3 =====");


    }

}


