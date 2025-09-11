package com.ppi.trackventory.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "FORMS")
@SequenceGenerator(name = "form_seq", sequenceName = "FORM_SEQ", allocationSize = 1)
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

    @Column(name = "VISIBLE", nullable = false)
    private boolean visible = true; 

    // relación con permisos
    @OneToMany(mappedBy = "formPms", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Permission> permisos = new ArrayList<>();

    // relación jerárquica consigo mismo (padre → hijos)
    @ManyToOne
    @JoinColumn(name = "PARENT_FORM_ID")
    private Form parent;
    
    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Form> children = new ArrayList<>();

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<Permission> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permission> permisos) {
        this.permisos = permisos;
    }

    public Form getParent() {
        return parent;
    }

    public void setParent(Form parent) {
        this.parent = parent;
    }

    public List<Form> getChildren() {
        return children;
    }

    public void setChildren(List<Form> children) {
        this.children = children;
    }
}
