package fr.sparkit.starkoauthmicroservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MasterCompany")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel(value = "All about company")
@ToString
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @EqualsAndHashCode.Include
    @ApiModelProperty("The database generate company Id")
    private Integer id;

    @Column(name = "Code", columnDefinition = "varchar(50)")
    @ApiModelProperty("Company code")
    private String code;

    @Column(name = "Name", columnDefinition = "varchar(255)")
    @ApiModelProperty("Company name")
    private String name;

    @Column(name = "Email", columnDefinition = "varchar(255)")
    @ApiModelProperty("Company email")
    private String email;

    @Column(name = "IsDeleted", nullable = false)
    @ApiModelProperty("Is company deleted")
    private boolean isDeleted;

    @Column(name = "TransactionUserId")
    @ApiModelProperty("Company user transaction id")
    private Integer transactionUserId;

    @Column(name = "Deleted_Token", columnDefinition = "varchar(255)")
    @ApiModelProperty("Company deleted token")
    private String deletedToken;

    @Column(name = "DataBaseName", nullable = false, columnDefinition = "varbinary(4096)")
    @ApiModelProperty("Company database name")
    private String dataBaseName;

    @ManyToOne
    @JoinColumn(name = "IdMasterDbSettings")
    @ApiModelProperty("Company db settings")
    private DbSettings dbSettings;

    @Column(name = "DefaultLanguage")
    @ApiModelProperty("Company DefaultLanguage")
    private String defaultLanguage;

    @Column(name = "GarageDataBaseName", columnDefinition = "varbinary(4096)")
    @ApiModelProperty("garage database name")
    private String garageDataBaseName;



    @Column(name = "RHPaieDataBaseName", columnDefinition = "varbinary(4096)")
    @ApiModelProperty("Rh paie database name")
    private String rHPaieDataBaseName;

    @Column(name = "SettingsDataBaseName", columnDefinition = "varbinary(4096)")
    @ApiModelProperty("Settings database name")
    private String settingsDataBaseName;

}
