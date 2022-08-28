package fr.sparkit.starkoauthmicroservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    @JsonProperty("IdUser")
    private Integer id;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Login")
    private String login;
    @JsonProperty("LastConnectedCompanyId")
    private Integer lastConnectedCompanyId;
    @JsonProperty("LastConnectedCompany")
    private String lastConnectedCompany;
    @JsonProperty("Language")
    private String Language;
}
