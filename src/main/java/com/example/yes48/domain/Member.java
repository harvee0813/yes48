package com.example.yes48.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String zipcode;
    private String street;
    private String state;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
