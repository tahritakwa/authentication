package fr.sparkit.starkoauthmicroservice.controller;

import fr.sparkit.starkoauthmicroservice.auditing.HasRoles;
import fr.sparkit.starkoauthmicroservice.constants.SwaggerApiMessageConstant;
import fr.sparkit.starkoauthmicroservice.dto.CompanyDto;
import fr.sparkit.starkoauthmicroservice.dto.UserDto;
import fr.sparkit.starkoauthmicroservice.dto.UserRequestDto;
import fr.sparkit.starkoauthmicroservice.model.Role;
import fr.sparkit.starkoauthmicroservice.service.IUserService;
import fr.sparkit.starkoauthmicroservice.util.Credential;
import fr.sparkit.starkoauthmicroservice.util.RefreshToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
@CrossOrigin("*")
public class UserRestController {

    private IUserService userService;

    @Autowired
    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "token generated successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "retrieve token", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/token/retrieve-token")
    public Object retrieveToken(@RequestBody Credential credential, @RequestHeader("ProjectName") String projectName) {
        return userService.retrieveToken(credential, projectName);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "token generated successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "retrieve token using refresh token", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/token/retrieve-token-using-refresh-token")
    public Object retrieveTokenUsingRefreshToken(@RequestBody RefreshToken refreshToken) {
        return userService.retrieveTokenUsingRefreshToken(refreshToken);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "token generated successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "retrieve token using expired access token", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/token/retrieve-token-using-email")
    public Object retrieveTokenUsingEmail(@RequestBody UserRequestDto userRequestDto) {
        return userService.retrieveTokenUsingEmail(userRequestDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "token revoked successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "revoke token", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/token/revoke-token")
    public boolean revokeToken(@ApiParam(required = true, value = "email", name = "email") @RequestBody UserRequestDto userRequestDto) {
        return userService.revokeToken(userRequestDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The roles have been added to the user successfully.", response = Exception.class),
            @ApiResponse(code = 400, message = SwaggerApiMessageConstant.BAD_REQUEST, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Add roles to user", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/roles")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"USER_MANAGEMENT_SETTINGS"})
    public List<Role> addRolesToUser(
            @ApiParam(required = true, value = "email", name = "email") @RequestParam String email,
            @RequestBody List<Integer> rolesIds) {
        return userService.addRolesToUser(email, rolesIds);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An array of roles was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get roles by user and company", response = UserRestController.class, httpMethod = "GET")
    @GetMapping("/roles")
    @PreAuthorize("isAuthenticated()")
    public List<Role> findRolesByUserAndCompany(
            @ApiParam(required = true, value = "email", name = "email") @RequestParam String email,
            @ApiParam(required = true, value = "companyId", name = "companyId") @RequestParam Integer companyId) {
        return userService.findRolesByUserAndCompany(email, companyId);
    }

    @ApiResponses(value = {@ApiResponse(code = 201, message = "User has roles.", response = Exception.class),
            @ApiResponse(code = 400, message = SwaggerApiMessageConstant.BAD_REQUEST, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "check that user has roles", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/checkRoles")
    @PreAuthorize("isAuthenticated()")
    public boolean checkUserHasRoles(@RequestHeader("Authorization") String token,
                                     @RequestBody List<String> permissions) {
        return userService.checkUserHasRoles(token, permissions);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A string array of user was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get users by roles", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/users-by-roles")
    @PreAuthorize("isAuthenticated()")
    public Collection<String> getUsersByRoles(@RequestBody List<String> permissions,
                                              @ApiParam(required = true, value = "companyCode", name = "companyCode") @RequestParam String companyCode) {
        return userService.getUsersByRoles(permissions, companyCode);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A string array of authorities was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get user authorities", response = UserRestController.class, httpMethod = "GET")
    @GetMapping("/authorities")
    @PreAuthorize("isAuthenticated()")
    public List<String> getUserAuthorities(@RequestHeader("Authorization") String token) {
        return userService.getUserAuthorities(token);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The company has been successfully changed.", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Change company", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/switch-company")
    @PreAuthorize("isAuthenticated()")
    public boolean changeCompany(@ApiParam(required = true, value = "code", name = "code") @RequestBody UserRequestDto userRequestDto,
                                 @RequestHeader("Authorization") String token) {
        return userService.changeUsersCompanyUsingToken(userRequestDto.getCompanyCode(), token);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An array of company was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get company by user", response = UserRestController.class, httpMethod = "GET")
    @GetMapping("/user-with-associated-companies")
    public List<CompanyDto> getUserWithAssociatedCompanies(@RequestParam String email) {
        return userService.getUserWithAssociatedCompanies(email);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User language changed successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "change user language", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/change-user-language")
    public boolean changeUserLanguage(@RequestBody UserRequestDto userRequestDto) {
        return userService.changeUserLanguage(userRequestDto);
    }


    @PostMapping("/user-info")
    public UserDto getUserInfo(@RequestBody UserRequestDto userRequestDto){
        return userService.getUserInfo(userRequestDto.getEmail());
    }


}
