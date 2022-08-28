package fr.sparkit.starkoauthmicroservice.controller;


import fr.sparkit.starkoauthmicroservice.constants.SwaggerApiMessageConstant;
import fr.sparkit.starkoauthmicroservice.dto.CompanyDto;
import fr.sparkit.starkoauthmicroservice.dto.UserRequestDto;
import fr.sparkit.starkoauthmicroservice.service.IUserService;
import fr.sparkit.starkoauthmicroservice.service.impl.TokenRequestUtils;
import fr.sparkit.starkoauthmicroservice.util.AccessTokenRequestParam;
import fr.sparkit.starkoauthmicroservice.util.RefreshTokenRequestParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class B2bResourceRestController {


    private TokenRequestUtils tokenRequestUtils;
    private IUserService userService;

    @Autowired
    public B2bResourceRestController(TokenRequestUtils tokenRequestUtils, IUserService userService) {
        this.tokenRequestUtils = tokenRequestUtils;
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "token generated successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "retrieve token", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/retrieve-token")
    public Object retrieveB2bAccessToken(@RequestBody AccessTokenRequestParam tokenRequestParam) {
        this.userService.checkIfBtoBUser(tokenRequestParam.getCredential().getEmail());
        return tokenRequestUtils.accessTokenRequest(tokenRequestParam.getCredential(), tokenRequestParam.getOauth2ClientCredential());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "token generated successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "retrieve token using refresh token", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/retrieve-token-using-refresh-token")
    public Object retrieveB2bAccessTokenUsingRefreshToken(@RequestBody RefreshTokenRequestParam tokenRequestParam) {
        return tokenRequestUtils.refreshTokenRequest(tokenRequestParam.getRefreshToken(), tokenRequestParam.getOauth2ClientCredential());
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "token revoked successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "revoke token", response = UserRestController.class, httpMethod = "POST")
    @PostMapping("/revoke-token")
    public boolean revokeToken(@ApiParam(required = true, value = "email", name = "email") @RequestBody UserRequestDto userRequestDto) {
        return userService.revokeToken(userRequestDto);
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
}
