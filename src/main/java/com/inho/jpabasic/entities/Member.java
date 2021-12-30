package com.inho.jpabasic.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
public class Member
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    public Member(){}

    public Member(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
