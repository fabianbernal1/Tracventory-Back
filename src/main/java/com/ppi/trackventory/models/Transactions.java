package com.ppi.trackventory.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "TRANSACTIONS")
@SequenceGenerator(name = "tran_seq", sequenceName = "TRAN_SEQ", allocationSize = 1)
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tran_seq")
    @Column(name = "ID", length = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USER_BUYER", referencedColumnName = "ID")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "ID_USER_SELLER", referencedColumnName = "ID")
    private User seller;

    @Column(name = "TRANSACTION_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_TYPE", referencedColumnName = "ID")
    private TransactionTypes transactionType;
    
    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ORIGIN", referencedColumnName = "ID")
    private TransactionOrigins transactionOrigin;
    
    @Column(name = "ENABLED")
    private Boolean enabled;

    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypes transactionType) {
        this.transactionType = transactionType;
    }

	public TransactionOrigins getTransactionOrigin() {
		return transactionOrigin;
	}

	public void setTransactionOrigin(TransactionOrigins transactionOrigin) {
		this.transactionOrigin = transactionOrigin;
	}
    
}
