package com.victorbarbosa.fintracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_authorities")
public class Authority {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Authority() {
    }

    public Long getId() {
        return id;
    }

    public Authority(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public static class Values {
        public static final String TRANSACTION_CREATE = "transaction:create";
        public static final String TRANSACTION_READ = "transaction:read";
        public static final String TRANSACTION_UPDATE = "transaction:update";
        public static final String TRANSACTION_DELETE = "transaction:delete";

        public static final String USER_CREATE = "user:create";
        public static final String USER_READ = "user:read";
        public static final String USER_UPDATE = "user:update";
        public static final String USER_DELETE = "user:delete";

        public static final String CATEGORY_CREATE = "category:create";
        public static final String CATEGORY_READ = "category:read";
        public static final String CATEGORY_UPDATE = "category:update";
        public static final String CATEGORY_DELETE = "category:delete";

        public static final String SETTINGS_UPDATE = "settings:update";
    }
}
