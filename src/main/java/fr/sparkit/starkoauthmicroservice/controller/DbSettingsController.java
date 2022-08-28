package fr.sparkit.starkoauthmicroservice.controller;




import fr.sparkit.starkoauthmicroservice.constants.SwaggerApiMessageConstant;
import fr.sparkit.starkoauthmicroservice.dao.CompanyRepository;
import fr.sparkit.starkoauthmicroservice.dao.DbSettingsRepository;
import fr.sparkit.starkoauthmicroservice.dto.DbSettingsDto;
import fr.sparkit.starkoauthmicroservice.model.Company;
import fr.sparkit.starkoauthmicroservice.model.DbSettings;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth/dbsettings")
@CrossOrigin("*")
public class DbSettingsController {




    private CompanyRepository companyRepository;



    private DbSettingsRepository dbSettingsRepository;




    public DbSettingsController(CompanyRepository companyRepository , DbSettingsRepository dbSettingsRepository){
        this.companyRepository = companyRepository;
        this.dbSettingsRepository = dbSettingsRepository;
    }




    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A db setting was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get Db settings", response = UserRestController.class, httpMethod = "GET")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{companyCode}")
    DbSettingsDto getDbSettings(@PathVariable String companyCode){
        Company company = companyRepository.findByCodeAndIsDeletedFalse(companyCode).orElseThrow(() -> new HttpCustomException(ApiErrorCodes.COMPANY_WITH_CODE_NOT_FOUND));
        DbSettings dbSettings = dbSettingsRepository.getOne(1);
        return new DbSettingsDto(dbSettings.getServer(),dbSettings.getUserId(),dbSettings.getUserPassword(),company.getDataBaseName(),company.getRHPaieDataBaseName(),company.getGarageDataBaseName(),company.getSettingsDataBaseName());
    }



}
