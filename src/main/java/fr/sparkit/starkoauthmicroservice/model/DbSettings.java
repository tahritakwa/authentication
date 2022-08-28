package fr.sparkit.starkoauthmicroservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MasterDbSettings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel(value = "All about DbSetting")
public class DbSettings implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @EqualsAndHashCode.Include
    @ApiModelProperty("The database generate Dbsetting Id")
    private Integer id;

    @Column(name = "Server", columnDefinition = "varbinary(4096)", nullable = false)
    @ApiModelProperty("DbSetting server")
    private String server;

    @Column(name = "UserId", columnDefinition = "varbinary(4096)", nullable = false)
    @ApiModelProperty("DbSetting user id")
    private String userId;

    @Column(name = "UserPassword", columnDefinition = "varbinary(4096)", nullable = false)
    @ApiModelProperty("DbSetting user password")
    private String userPassword;

}
