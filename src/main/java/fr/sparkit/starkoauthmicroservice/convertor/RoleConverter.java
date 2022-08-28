package fr.sparkit.starkoauthmicroservice.convertor;

import fr.sparkit.starkoauthmicroservice.dto.RoleDto;
import fr.sparkit.starkoauthmicroservice.model.Permission;
import fr.sparkit.starkoauthmicroservice.model.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RoleConverter {
    private RoleConverter() {
        super();
    }

    public static RoleDto modelToDTO(Role role) {
        return new RoleDto(role.getId(), role.getCode(), role.getLabel(),
                role.getPermissions().stream().map(Permission::getId).collect(Collectors.toList()),
                role.getCompany().getId(),role.getCompany().getCode());
    }

    public static List<RoleDto> modelsToDTOs(Collection<Role> roles) {
        if(roles.isEmpty()){
            return new ArrayList<>();
        }
        return roles.stream().filter(Objects::nonNull).map(RoleConverter::modelToDTO).collect(Collectors.toList());
    }
}
