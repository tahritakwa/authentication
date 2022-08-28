package fr.sparkit.starkoauthmicroservice.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MasterRolePermission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel("All details about role permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @EqualsAndHashCode.Include
    @ApiModelProperty("The database generate role permission Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdRole", nullable = false)
    @ApiModelProperty("Role")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "IdPermission", nullable = false)
    @ApiModelProperty("Permission")
    private Permission permission;

    @Column(name = "IsDeleted", nullable = false)
    @ApiModelProperty("Is role permission deleted")
    private boolean isDeleted;

    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }
}
