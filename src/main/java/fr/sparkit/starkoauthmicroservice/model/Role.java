package fr.sparkit.starkoauthmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "MasterRole")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel("All details about role")
@ToString
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @JsonProperty("Id")
    @EqualsAndHashCode.Include
    @ApiModelProperty("The database generate role Id")
    private Integer id;

    @Column(name = "Code")
    @JsonProperty("Code")
    @ApiModelProperty("Role code")
    private String code;

    @Column(name = "Label")
    @JsonProperty("Label")
    @ApiModelProperty("Role label")
    private String label;

    @Transient
    @JsonProperty("Permissions")
    @ApiModelProperty("Role permissions")
    private List<Permission> permissions;

    @ManyToOne
    @JoinColumn(name = "IdCompany")
    @JsonIgnore
    @ApiModelProperty("Role company")
    private Company company;

    public Role(Integer id, String code, String label, Company company) {
        this.id = id;
        this.code = code;
        this.label = label;
        this.company = company;
    }
}
