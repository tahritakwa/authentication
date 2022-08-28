package fr.sparkit.starkoauthmicroservice.service.impl;

import fr.sparkit.starkoauthmicroservice.convertor.RoleConverter;
import fr.sparkit.starkoauthmicroservice.dao.*;
import fr.sparkit.starkoauthmicroservice.dto.RoleDto;
import fr.sparkit.starkoauthmicroservice.model.*;
import fr.sparkit.starkoauthmicroservice.service.IRoleService;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.errors.ErrorsResponse;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleService implements IRoleService {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private CompanyRepository companyRepository;
    private RolePermissionRepository rolePermissionRepository;
    private RoleUserRepository roleUserRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository,
                       CompanyRepository companyRepository, RolePermissionRepository rolePermissionRepository, RoleUserRepository roleUserRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.companyRepository = companyRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleUserRepository = roleUserRepository;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "UserAuthoritiesCache", allEntries = true)})
    public RoleDto addRole(RoleDto roleDto) {
        log.info("saving role {}", roleDto.getCode());
        List<Permission> permissions = permissionRepository.findByIdIn(roleDto.getPermissions());

        Company company = companyRepository.findByCodeAndIsDeletedFalse(roleDto.getCompanyCode())
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.COMPANY_WITH_ID_NOT_FOUND,
                        new ErrorsResponse().error(roleDto.getCompanyId())));

        if (roleDto.getId() != null) {
            List<RolePermission> rolePermissions = rolePermissionRepository.findByRole_Id(roleDto.getId());
            rolePermissions.forEach(rolePermission -> rolePermissionRepository.deleteById(rolePermission.getId()));
        }
        Role role = roleRepository.save(new Role(roleDto.getId(), roleDto.getCode(), roleDto.getLabel(), company));
        permissions.forEach(permission -> rolePermissionRepository.save(new RolePermission(role, permission)));
        role.setPermissions(permissions);
        return RoleConverter.modelToDTO(role);
    }

    @Override
    @Transactional
    public Role getRoleById(Integer id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new HttpCustomException(ApiErrorCodes.NO_ROLE_WITH_ID, new ErrorsResponse().error(id)));
        List<Permission> permissionsForCurrentRole = rolePermissionRepository.findByRole_Id(role.getId())
                .stream().map(RolePermission::getPermission).collect(Collectors.toList());
        role.setPermissions(permissionsForCurrentRole);
        return role;
    }

    @Override
    public Page<RoleDto> getRolesByCompany(String companyCode, Pageable pageable) {
        Optional<Company> company = companyRepository.findByCodeAndIsDeletedFalse(companyCode);
        List<Role> roles = new ArrayList<>();
        if(company.isPresent()){
            Integer companyId = company.get().getId();
            Page<Role> rolesPage = roleRepository.findByCompanyId(companyId, pageable);
             roles = rolesPage.getContent();
            roles.forEach(role -> role.setPermissions(new ArrayList<>()));
            return new PageImpl<>(RoleConverter.modelsToDTOs(roles), pageable, rolesPage.getTotalElements());
        }
        return new PageImpl<>(RoleConverter.modelsToDTOs(roles), pageable, 0);

    }

    @Override
    public List<Role> getAllRolesByCompany(String companyCode) {
        Optional<Company> company = companyRepository.findByCodeAndIsDeletedFalse(companyCode);
        List<Role> roles = new ArrayList<>();
        if(company.isPresent()){
            Integer companyId = company.get().getId();
            roles = roleRepository.findByCompanyId(companyId);
            roles.forEach(role -> role.setPermissions(new ArrayList<>()));
            return roles;
        }
        return roles;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "UserAuthoritiesCache", allEntries = true)})
    @Transactional
    public RoleDto updateRole(RoleDto role, Integer id) {
        return addRole(role);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "UserAuthoritiesCache", allEntries = true)})
    @Transactional
    public boolean deleteRoleById(Integer id) {
        List<RoleUser> roleUsers = roleUserRepository.findByRole_Id(id);
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRole_Id(id);
        if (!roleUsers.isEmpty()) {
            throw new HttpCustomException(ApiErrorCodes.CURRENT_ROLE_IN_USE);
        }
        if (!rolePermissions.isEmpty()) {
            rolePermissions.forEach(rolePermission -> rolePermissionRepository.deleteById(rolePermission.getId()));
        }
        roleRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Role> getRolesByUser(Integer userId) {
        List<RoleUser> roles = roleUserRepository.findByUser_Id(userId);
        List<Role> roleList = new ArrayList<Role>();
        roles.forEach(role -> roleList.add(role.getRole()));
        return roleList;
    }

}
