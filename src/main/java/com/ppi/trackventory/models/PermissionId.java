package com.ppi.trackventory.models;

import java.io.Serializable;
import java.util.Objects;

public class PermissionId implements Serializable {
    private Long profilePms; 
    private Integer formPms; 

    public PermissionId() {}

    public PermissionId(Long profilePms, Integer formPms) {
        this.profilePms = profilePms;
        this.formPms = formPms;
    }

    public Long getProfilePms() {
        return profilePms;
    }

    public void setProfilePms(Long profilePms) {
        this.profilePms = profilePms;
    }

    public Integer getFormPms() {
        return formPms;
    }

    public void setFormPms(Integer formPms) {
        this.formPms = formPms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profilePms, formPms);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PermissionId that = (PermissionId) obj;
        return Objects.equals(profilePms, that.profilePms) &&
               Objects.equals(formPms, that.formPms);
    }
}
