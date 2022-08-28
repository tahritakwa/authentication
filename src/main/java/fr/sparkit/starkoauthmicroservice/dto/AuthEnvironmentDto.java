package fr.sparkit.starkoauthmicroservice.dto;


import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthEnvironmentDto {
    private String buildVersion;
    private String authServerUrl;
}
