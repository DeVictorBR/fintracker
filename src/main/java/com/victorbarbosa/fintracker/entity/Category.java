package com.victorbarbosa.fintracker.entity;

import com.victorbarbosa.fintracker.enums.TransactionType;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "name"})
})
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    public Category() {
    }

    public Category(String name, String description, User user, TransactionType type) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
