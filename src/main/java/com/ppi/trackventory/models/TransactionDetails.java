package com.ppi.trackventory.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_DETAILS")
@SequenceGenerator(name = "tranDet_seq", sequenceName = "TRANDET_SEQ", allocationSize = 1)
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tranDet_seq")
    @Column(name = "ID_DETAIL", length = 20)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TRANSACTION", referencedColumnName = "ID")
    private Transactions transaction;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_STORE", referencedColumnName = "STORE"),
        @JoinColumn(name = "ID_VARIATION", referencedColumnName = "VARIATION")
    })
    private Stock stock;

    @Column(name = "QUANTITY")
    private int quantity;
    
    @Column(name = "DISCOUNT_PERCENTAGE")
    private int discount_percentage;

    @Column(name = "TOTAL", precision = 12, scale = 2)
    private BigDecimal total;
    
    @Column(name = "ENABLED")
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getDiscount_percentage() {
		return discount_percentage;
	}

	public void setDiscount_percentage(int discount_percentage) {
		this.discount_percentage = discount_percentage;
	}
    
}
