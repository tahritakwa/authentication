package fr.sparkit.starkoauthmicroservice.controller;

import fr.sparkit.starkoauthmicroservice.constants.SwaggerApiMessageConstant;
import fr.sparkit.starkoauthmicroservice.model.SubModule;
import fr.sparkit.starkoauthmicroservice.service.impl.SubModuleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/submodule")
public class SubModuleController {

    private final SubModuleService subModuleService;

    @Autowired
    public SubModuleController(SubModuleService subModuleService) {
        this.subModuleService = subModuleService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A submodule was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Find submodule by id", response = SubModuleController.class, httpMethod = "GET")
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}")
    public SubModule getSubModule(@ApiParam(required = true, value = "id", name = "id") @PathVariable Integer id) {
        return subModuleService.getSubModule(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An array of submodules was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get all submodules", response = SubModuleController.class, httpMethod = "GET")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/submodules")
    public List<SubModule> getSubModules() {
        return subModuleService.findAll();
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "submodule deleted successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Delete submodule", response = SubModuleController.class, httpMethod = "DELETE")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/{id}")
    public boolean deleteSubModule(@ApiParam(required = true, value = "id", name = "id") @PathVariable Integer id) {
        return subModuleService.deleteSubModule(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "submodule object created successfully", response = Exception.class),
            @ApiResponse(code = 400, message = SwaggerApiMessageConstant.BAD_REQUEST, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Save submodule", response = SubModuleController.class, httpMethod = "POST")
    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public SubModule save(@RequestBody SubModule subModule) {
        return subModuleService.save(subModule);
    }

    @ApiResponses(value = {@ApiResponse(code = 201, message = "submodule updated successfully", response = Exception.class),
            @ApiResponse(code = 400, message = SwaggerApiMessageConstant.BAD_REQUEST, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Update submodule", response = SubModuleController.class, httpMethod = "PUT")
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/{id}")
    public SubModule updateSubModule(@RequestBody SubModule subModule) {
        return subModuleService.update(subModule);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A page of submodules was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get all submodules", response = SubModuleController.class, httpMethod = "GET")
    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public Page<SubModule> getSubModules(Pageable pageable) {
        return subModuleService.findAll(pageable);
    }
}
