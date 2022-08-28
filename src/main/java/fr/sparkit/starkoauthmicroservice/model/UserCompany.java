package fr.sparkit.starkoauthmicroservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MasterUserCompany")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel("All details about user company")
public class UserCompany implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @EqualsAndHashCode.Include
    @ApiModelProperty("The database generate user company Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdMasterUser", nullable = false)
    @ApiModelProperty("User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "IdMasterCompany", nullable = false)
    @ApiModelProperty("Company")
    private Company company;

    @Column(name = "IsDeleted", nullable = false)
    @ApiModelProperty("Is user company deleted")
    private boolean isDeleted;

    @Column(name = "TransactionUserId", nullable = false)
    @ApiModelProperty("User transaction id")
    private Integer transactionUserId;

    @Column(name = "Deleted_Token", columnDefinition = "varchar(255)")
    @ApiModelProperty("User company deleted token")
    private String deletedToken;

    @Column(name = "IsActif", nullable = false)
    @ApiModelProperty("Is user company active")
    private boolean isActif;

}
