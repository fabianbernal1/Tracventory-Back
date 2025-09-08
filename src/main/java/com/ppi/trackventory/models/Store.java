package com.ppi.trackventory.models;

import javax.persistence.*;

@Entity
@Table(name = "STORES")
public class Store {

    @Id
    @Column(name = "STORE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "ADDRESS", length = 100)
    private String address;

    @Column(name = "ENABLED")
    private Boolean enabled;

    // --- Getters & Setters ---
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
