package fr.sparkit.starkoauthmicroservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "MasterSubModule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel("All details about submodule")
public class SubModule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "Id")
    @ApiModelProperty("The database generate submodule Id")
    private Integer id;

    @Column(name = "Code", unique = true)
    @ApiModelProperty("Submodule code")
    private String code;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "IdSubModule")
    @ApiModelProperty("Submodule permissions")
    private List<Permission> permissions;
}
