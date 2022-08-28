package fr.sparkit.starkoauthmicroservice.service;

import fr.sparkit.starkoauthmicroservice.dto.RoleDto;
import fr.sparkit.starkoauthmicroservice.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoleService {

    RoleDto addRole(RoleDto role);

    Role getRoleById(Integer id);

    Page<RoleDto> getRolesByCompany(String companyCode, Pageable pageable);

    List<Role> getAllRolesByCompany(String companyCode);

    RoleDto updateRole(RoleDto role, Integer id);

    boolean deleteRoleById(Integer id);

    List<Role> getRolesByUser(Integer userId);

}
