package fr.sparkit.starkoauthmicroservice.service;

import fr.sparkit.starkoauthmicroservice.dto.CompanyDto;
import fr.sparkit.starkoauthmicroservice.dto.UserDto;
import fr.sparkit.starkoauthmicroservice.dto.UserRequestDto;
import fr.sparkit.starkoauthmicroservice.model.Role;
import fr.sparkit.starkoauthmicroservice.util.Credential;
import fr.sparkit.starkoauthmicroservice.util.RefreshToken;

import java.util.Collection;
import java.util.List;

public interface IUserService {

    Object retrieveToken(Credential credential, String projectName);

    Object retrieveTokenUsingRefreshToken(RefreshToken refreshToken);

    Object retrieveTokenUsingEmail(UserRequestDto userRequestDto);

    List<Role> addRolesToUser(String email, List<Integer> rolesIds);

    List<Role> findRolesByUserAndCompany(String email, Integer companyId);

    boolean checkUserHasRoles(String token, List<String> permissions);

    List<String> getUserAuthorities(String token);

    boolean changeUsersCompanyUsingToken(String companyCode, String token);

    Collection<String> getUsersByRoles(List<String> permissions, String companyCode);

    List<CompanyDto> getUserWithAssociatedCompanies(String email);

    boolean revokeToken(UserRequestDto userRequestDto);

    boolean changeUserLanguage(UserRequestDto userRequestDto);

    void checkIfBtoBUser(String email);

    UserDto getUserInfo(String email);

}
