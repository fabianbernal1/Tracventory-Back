package com.ppi.trackventory.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COLORS")
@SequenceGenerator(name = "color_seq", sequenceName = "COLOR_SEQ", allocationSize = 1)
public class Color {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "color_seq")
    @Column(name = "ID_COLOR", length = 4)
    private Integer id;
	
    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "HEX_CODE", length = 7)
    private String hexCode;
    
    @Column(name = "ENABLED")
    private Boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHexCode() {
		return hexCode;
	}

	public void setHexCode(String hexCode) {
		this.hexCode = hexCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}