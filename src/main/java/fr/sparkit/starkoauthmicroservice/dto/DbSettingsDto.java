package fr.sparkit.starkoauthmicroservice.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbSettingsDto {



    private String Server;
    private String UserId;
    private String UserPassword;
    private String DataBaseName;
    private String RHPaieDataBaseName;
    private String GarageDataBaseName;
    private String SettingsDataBaseName;
}