package fr.sparkit.starkoauthmicroservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Label")
    private String label;
    @JsonProperty("Permissions")
    private List<Integer> permissions;
    @JsonProperty("CompanyId")
    private Integer companyId;
    @JsonProperty("CompanyCode")
    private String companyCode;
}
