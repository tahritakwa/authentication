package fr.sparkit.starkoauthmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String language;
    private String accessToken;
    private String companyCode;


    public UserRequestDto(String email){
        this.email = email;
    }
}
