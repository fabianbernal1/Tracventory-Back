package com.ppi.trackventory.models;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_VARIATIONS")
public class ProductVariation {

    @Id
    @Column(name = "VARIATION_ID", length = 50)
    private String variationId;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "COLOR_ID", referencedColumnName = "ID_COLOR")
    private Color color;

    @Column(name = "ENABLED")
    private Boolean enabled;

    // Getters & Setters
    public String getVariationId() {
        return variationId;
    }
    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
