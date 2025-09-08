package com.ppi.trackventory.models;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "FORMS")
@SequenceGenerator(name = "form_seq", sequenceName = "FORM_SEQ", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Form {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_seq")
    @Column(name = "ID_FORM", length = 4)
    private Integer id;
	
    @Column(name = "URL", length = 50)
    private String url;

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "ICON", length = 30)
    private String icon;
    
    @OneToMany(mappedBy = "formPms", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  
    private List<Permission> permisos = new ArrayList<>();


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String description) {
		this.icon = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
