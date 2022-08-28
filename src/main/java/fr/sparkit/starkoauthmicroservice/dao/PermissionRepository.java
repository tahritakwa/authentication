package fr.sparkit.starkoauthmicroservice.dao;

import fr.sparkit.starkoauthmicroservice.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByIdIn(List<Integer> ids);
}
