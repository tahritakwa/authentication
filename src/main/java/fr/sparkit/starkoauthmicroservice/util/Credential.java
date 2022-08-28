package fr.sparkit.starkoauthmicroservice.util;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credential {
    private String email;
    private String password;
}
