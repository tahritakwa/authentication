package fr.sparkit.starkoauthmicroservice.model;

import fr.sparkit.starkoauthmicroservice.auditing.LocalDateTimeAttributeConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "CompanyLicence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel(value = "All about CompanyLicence")
public class CompanyLicence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @EqualsAndHashCode.Include
    @ApiModelProperty("The database generate company Id")
    private Integer id;

    @Column(name = "NombreERPUser")
    private Long numberERPUser;

    @Column(name = "NombreB2BUser")
    private Long numberB2BUser;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "ExpirationDate")
    private LocalDateTime expirationDate;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "IntialDate")
    private LocalDateTime intialDate;

    @Column(name = "IsDeleted", columnDefinition = "bit default 0")
    private boolean isDeleted;

    @Column(name = "Deleted_Token")
    private UUID deletedToken;

    @OneToOne
    @JoinColumn(name = "IdMasterCompany")
    private Company company;
}
