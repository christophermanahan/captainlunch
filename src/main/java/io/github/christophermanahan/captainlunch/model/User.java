package io.github.christophermanahan.captainlunch.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="identity", nullable = false, unique = true)
    private String identity;

    public User(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
