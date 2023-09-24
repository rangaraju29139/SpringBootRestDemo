package com.example.springbootrestdemo.users;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String name ;
    private String role;

    public UserDetails() {
    }

    public UserDetails(String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
