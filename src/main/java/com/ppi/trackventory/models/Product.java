package com.ppi.trackventory.models;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID", length = 50)
    private String productId;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "PURCHASE_PRICE", precision = 12, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "SALE_PRICE", precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "PROFIT_MARGIN", precision = 5, scale = 0)
    private Integer profitMargin;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    private ProductCategory category;

    // Getters & Setters
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getProfitMargin() {
        return profitMargin;
    }
    public void setProfitMargin(Integer profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public ProductCategory getCategory() {
        return category;
    }
    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
