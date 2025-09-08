package com.ppi.trackventory.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class StockId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "STORE", referencedColumnName = "STORE_ID")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "VARIATION", referencedColumnName = "VARIATION_ID")
    private ProductVariation variation;

    public StockId() {
    }

    public StockId(Store store, ProductVariation variation) {
        this.store = store;
        this.variation = variation;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ProductVariation getVariation() {
        return variation;
    }

    public void setVariation(ProductVariation variation) {
        this.variation = variation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockId stockId = (StockId) o;
        return Objects.equals(store, stockId.store) &&
               Objects.equals(variation, stockId.variation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store, variation);
    }
}
