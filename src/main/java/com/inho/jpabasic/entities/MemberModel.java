package com.inho.jpabasic.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
@SequenceGenerator(name=SequenceNames.MEMBER_SEQ_GEN,
                sequenceName = SequenceNames.MEMBER_SEQ,
                initialValue = 1, allocationSize = 1)

public class MemberModel {
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE,
                    generator = SequenceNames.MEMBER_SEQ )
    private long id;
    private String name;

    public MemberModel() {
    }

    public MemberModel(String name) {
        this.name = name;
    }

    public MemberModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
