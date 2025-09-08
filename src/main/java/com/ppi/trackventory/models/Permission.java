package com.ppi.trackventory.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PERMISSIONS")
@IdClass(PermissionId.class)
public class Permission {
	@Id
    @ManyToOne
    @JoinColumn(name = "PROFILE_PMS", referencedColumnName = "ID")
	@MapsId("profileId")
	@JsonProperty("profilePms")
    private Profile profilePms;

    @Id
    @ManyToOne
    @JoinColumn(name = "FORM_PMS", referencedColumnName = "ID_FORM")
    @MapsId("formId")
    @JsonProperty("formPms")
    private Form formPms;
    
    @JsonProperty("C")
    @Column(name = "C")
    private Boolean C;
    
    @JsonProperty("R")
    @Column(name = "R")
    private Boolean R;
    
    @JsonProperty("U")
    @Column(name = "U")
    private Boolean U;
    
    @JsonProperty("D")
    @Column(name = "D")
    private Boolean D;

	public Profile getProfile_pms() {
		return profilePms;
	}

	public void setProfile_pms(Profile profile_pms) {
		this.profilePms = profile_pms;
	}

	public Form getForm_pms() {
		return formPms;
	}

	public void setForm_pms(Form form_pms) {
		this.formPms = form_pms;
	}
	
	// Métodos para permisos (isCan...)
    public boolean isCanCreate() {
        return C != null && C;
    }

    public boolean isCanRead() {
        return R != null && R;
    }

    public boolean isCanUpdate() {
        return U != null && U;
    }

    public boolean isCanDelete() {
        return D != null && D;
    }

    // Setters adicionales
    public void setC(Boolean c) {
        this.C = c;
    }

    public void setR(Boolean r) {
        this.R = r;
    }

    public void setU(Boolean u) {
        this.U = u;
    }

    public void setD(Boolean d) {
        this.D = d;
    }
    
}
