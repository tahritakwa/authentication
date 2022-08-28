package fr.sparkit.starkoauthmicroservice.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MasterRoleUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel("All details about role user")
@ToString
public class RoleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @EqualsAndHashCode.Include
    @ApiModelProperty("The database generate role user Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdMasterUser", nullable = false)
    @ApiModelProperty("User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "IdRole", nullable = false)
    @ApiModelProperty("Role")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "IdMasterCompany", nullable = false)
    @ApiModelProperty("Company")
    private Company company;

    @Column(name = "IsDeleted", nullable = false, columnDefinition = "bit default 1")
    @ApiModelProperty("Is role user deleted")
    private boolean isDeleted;


    public RoleUser(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
