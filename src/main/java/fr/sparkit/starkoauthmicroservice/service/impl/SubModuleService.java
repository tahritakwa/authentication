package fr.sparkit.starkoauthmicroservice.service.impl;

import fr.sparkit.starkoauthmicroservice.dao.ActionPermissionRepository;
import fr.sparkit.starkoauthmicroservice.dao.RolePermissionRepository;
import fr.sparkit.starkoauthmicroservice.dao.RoleRepository;
import fr.sparkit.starkoauthmicroservice.dao.SubModuleRepository;
import fr.sparkit.starkoauthmicroservice.model.Permission;
import fr.sparkit.starkoauthmicroservice.model.Role;
import fr.sparkit.starkoauthmicroservice.model.RolePermission;
import fr.sparkit.starkoauthmicroservice.model.SubModule;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.errors.ErrorsResponse;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubModuleService {

    private SubModuleRepository subModuleRepository;
    private ActionPermissionRepository actionPermissionRepository;
    private RoleRepository roleRepository;
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    public SubModuleService(SubModuleRepository subModuleRepository,
                            ActionPermissionRepository actionPermissionRepository, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository) {
        this.subModuleRepository = subModuleRepository;
        this.actionPermissionRepository = actionPermissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public SubModule getSubModule(Integer id) {
        return subModuleRepository.findById(id)
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.SUB_MODULE_WITH_ID_NOT_FOUND,
                        new ErrorsResponse().error(id)));
    }

    public Page<SubModule> findAll(Pageable pageable) {
        return subModuleRepository.findAll(pageable);
    }

    public List<SubModule> findAll() {
        return subModuleRepository.findAll();
    }

    public boolean deleteSubModule(Integer id) {
        SubModule subModule = getSubModule(id);
        List<Permission> permissions = subModule.getPermissions();
        permissions.forEach(permission -> {
            List<Role> rolesWithPermission = rolePermissionRepository.findByPermission_Id(permission.getId()).stream().map(RolePermission::getRole).collect(Collectors.toList());
            if (!rolesWithPermission.isEmpty()) {
                String rolesWithPermissionIds = Arrays
                        .toString(rolesWithPermission.stream().map(Role::getId).toArray(Long[]::new));
                log.error(
                        "Unable to deleted SubModule with Id '{}' because the Permission with Id '{}' is used in Role(s) with Id : {}",
                        subModule.getId(), permission.getId(), rolesWithPermissionIds);
                throw new HttpCustomException(ApiErrorCodes.PERMISSION_USED_IN_ROLE,
                        new ErrorsResponse().error(permission.getId()).error(rolesWithPermissionIds));
            }
        });
        subModuleRepository.deleteById(id);
        actionPermissionRepository.deleteInBatch(permissions);
        return true;
    }

    public SubModule save(SubModule subModule) {
        checkValidCode(subModule.getCode());
        List<Permission> permissions = subModule.getPermissions();
        for (Permission permission : permissions) {
            if (actionPermissionRepository.findByCode(permission.getCode()).isPresent()) {
                throw new HttpCustomException(ApiErrorCodes.PERMISSION_CODE_EXISTS,
                        new ErrorsResponse().error(permission.getCode()));
            }
        }
        subModule.setPermissions(actionPermissionRepository.saveAll(permissions));
        return subModuleRepository.save(subModule);
    }

    private void checkValidCode(String code) {
        if (subModuleRepository.findByCode(code).isPresent()) {
            log.error("submodule code {} exists", code);
            throw new HttpCustomException(ApiErrorCodes.SUB_MODULE_CODE_EXISTS, new ErrorsResponse().error(code));
        }
    }

    public SubModule update(SubModule subModule) {
        SubModule oldSubModule = getSubModule(subModule.getId());
        if (!oldSubModule.getCode().equalsIgnoreCase(subModule.getCode())) {
            checkValidCode(subModule.getCode());
        }
        List<Permission> permissions = subModule.getPermissions();
        for (Permission permission : permissions) {
            if (permission.getId() == null) {// new permission
                if (actionPermissionRepository.findByCode(permission.getCode()).isPresent()) {
                    throw new HttpCustomException(ApiErrorCodes.PERMISSION_CODE_EXISTS,
                            new ErrorsResponse().error(permission.getCode()));
                }
            } else {
                if (!oldSubModule.getCode().equalsIgnoreCase(permission.getCode())) {
                    checkValidCode(subModule.getCode());
                }
            }
        }
        // for (Permission permission : permissions) {
        // if (actionPermissionRepository.findByCode(permission.getCode()).isPresent())
        // {
        // throw new HttpCustomException(ApiErrorCodes.ACTION_CODE_EXISTS,
        // new ErrorsResponse().error(permission.getCode()));
        // }
        // }
        for (Permission oldPermission : oldSubModule.getPermissions()) {
            Optional<Permission> oldPermissionInKeptPermissions = permissions.stream()
                    .filter(permission -> oldPermission.getId().equals(permission.getId())).findAny();
        }
        subModule.setPermissions(actionPermissionRepository.saveAll(permissions));
        return null;
    }
}
