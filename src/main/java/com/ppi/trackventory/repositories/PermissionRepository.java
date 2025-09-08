package com.ppi.trackventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.Permission;
import com.ppi.trackventory.models.PermissionId;
import com.ppi.trackventory.models.Profile;

public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {
	List<Permission> findByProfilePms(Profile profile);
}
