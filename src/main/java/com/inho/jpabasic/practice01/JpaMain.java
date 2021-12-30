package com.inho.jpabasic.practice01;

import com.inho.jpabasic.entities.MemberModel;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.List;


public class JpaMain
{
    public static void main(String[] args) {

        System.out.println(Charset.defaultCharset().displayName());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2Db");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{

            insert(em);
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
        for(int i=0; i<100; i++){
            int no = i + 1;
            MemberModel memberModel = new MemberModel( 0L, String.format( "오징어%d", no) );
            System.out.println("member = " + memberModel);
            em.persist(memberModel);
        }
    }

    public static void find(EntityManager em)
    {
        MemberModel findMemberModel = em.find(MemberModel.class, 1L);
        System.out.println("findMember = " + findMemberModel);
    }

    public static void remove(EntityManager em)
    {
        MemberModel findMemberModel = em.find(MemberModel.class, 2L);
        em.remove(findMemberModel);
    }

    public static void update(EntityManager em)
    {
        MemberModel findMemberModel = em.find(MemberModel.class, 1L);
        findMemberModel.setName("이인호*");
    }


    public static void jpql(EntityManager em)
    {
        String qlString = "select m from MemberModel as m";
        TypedQuery<MemberModel> query = em.createQuery(qlString, MemberModel.class);
        List<MemberModel> list = query
                                .setFirstResult(10) /* 0 부터 시작 = (page -1) * pageSize */
                                .setMaxResults(10)  /* 조회 건수   = pageSize */
                                .getResultList();
        for (MemberModel memberModel : list) {
            System.out.println("member = " + memberModel);
        }
    }


    public static void context(EntityManager em)
    {
        // 비영속성
        MemberModel memberModel = new MemberModel(1000L, "영속성");

        // 영속성 상태
        System.out.println("==== BEFORE =====");
        // 1차 캐시 저장 및 "쓰기 지연 SQL 저장소"에 INSERT SQL 생성 저장.
        em.persist(memberModel); // 영속상태 ( 이때 DB 저장 안됨 )
        System.out.println("==== AFTER  =====");

        // 1차 캐시의 내용 조회 ( SELECT 쿼리 실행 안함 )
        MemberModel findMemberModel = em.find(MemberModel.class, 1000L);

        // 동일성 보장 ( 객체 주소 동일 : 1차 캐시가 있어 가능 )
        boolean identity = memberModel == findMemberModel;

        // 준영속 상태(detached)
        em.detach(memberModel);

        // 삭제 상태
        em.remove(memberModel);

    }

    public static void contextQuery(EntityManager em)
    {
        // 영속성 컨텍스트 관리
        System.out.println("==== BEFORE #1 =====");
        MemberModel findMemberModel = em.find(MemberModel.class, 11L);
        System.out.println("==== AFTER  #1 =====");

        System.out.println("==== BEFORE  #1 AGAIN =====");
        MemberModel findMemberModel2 = em.find(MemberModel.class, 11L);
        System.out.println("findMember2 = " + (findMemberModel == findMemberModel2) );
        System.out.println("==== AFTER  #1 AGAIN =====");


        // 영속성 컨텍스트에 등록되는가?
        String qlString = "select m from MemberModel m";
        TypedQuery<MemberModel> query = em.createQuery(qlString, MemberModel.class);
        System.out.println("==== BEFORE #getResultList =====");
        List<MemberModel> list = query.getResultList();
        System.out.println("==== AFTER  #getResultList =====");

        System.out.println("==== BEFORE #2 =====");
        findMemberModel = em.find(MemberModel.class, 11L);
        System.out.println("==== AFTER  #2 =====");

        System.out.println("==== BEFORE #3 =====");
        findMemberModel = em.find(MemberModel.class, 20L);
        System.out.println("==== AFTER  #3 =====");


    }

}


