package com.ppi.trackventory.models;

import javax.persistence.*;

@Entity
@Table(name = "TRANSACTION_ORIGINS")
@SequenceGenerator(name = "origins_seq", sequenceName = "ORIGINS_SEQ", allocationSize = 1)
public class TransactionOrigins {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "origins_seq")
    @Column(name = "ID", length = 2)
    private Integer id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "ENABLED")
    private Boolean enabled;

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}