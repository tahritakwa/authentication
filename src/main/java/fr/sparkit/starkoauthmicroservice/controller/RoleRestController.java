package fr.sparkit.starkoauthmicroservice.controller;

import fr.sparkit.starkoauthmicroservice.auditing.HasRoles;
import fr.sparkit.starkoauthmicroservice.constants.SwaggerApiMessageConstant;
import fr.sparkit.starkoauthmicroservice.dto.RoleDto;
import fr.sparkit.starkoauthmicroservice.model.Role;
import fr.sparkit.starkoauthmicroservice.service.IRoleService;
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
@RequestMapping("/api/auth/roles")
public class RoleRestController {

    private IRoleService roleService;

    @Autowired
    public RoleRestController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "role object created successfully", response = Exception.class),
            @ApiResponse(code = 400, message = SwaggerApiMessageConstant.BAD_REQUEST, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Save role", response = RoleRestController.class, httpMethod = "POST")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"LIST_ROLE"})
    @PostMapping()
    public RoleDto addRole(@RequestBody RoleDto role) {
        return roleService.addRole(role);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A page of roles was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get all roles by company and page", response = RoleRestController.class, httpMethod = "GET")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"LIST_ROLE"})
    @GetMapping()
    public Page<RoleDto> getRolesByCompany(@ApiParam(required = true, value = "companyCode", name = "companyCode") @RequestParam String companyCode, Pageable pageable) {
        return roleService.getRolesByCompany(companyCode, pageable);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An array of roles was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get all roles by company", response = RoleRestController.class, httpMethod = "GET")
    @GetMapping("/roles")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"LIST_ROLE","USER_MANAGEMENT_SETTINGS"})
    public List<Role> getAllRolesByCompany(@ApiParam(required = true, value = "companyCode", name = "companyCode") @RequestParam String companyCode) {
        return roleService.getAllRolesByCompany(companyCode);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A role was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Find role by id", response = RoleRestController.class, httpMethod = "GET")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"LIST_ROLE"})
    @GetMapping("/{id}")
    public Role getRoleById(@ApiParam(required = true, value = "id", name = "id") @PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    @ApiResponses(value = {@ApiResponse(code = 201, message = "role updated successfully", response = Exception.class),
            @ApiResponse(code = 400, message = SwaggerApiMessageConstant.BAD_REQUEST, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Update role", response = RoleRestController.class, httpMethod = "PUT")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"LIST_ROLE"})
    @PutMapping("/{id}")
    public RoleDto updateRole(@RequestBody RoleDto role, @ApiParam(required = true, value = "id", name = "id") @PathVariable Integer id) {
        return roleService.updateRole(role, id);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "role deleted successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Delete role", response = RoleRestController.class, httpMethod = "DELETE")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"LIST_ROLE"})
    @DeleteMapping("/{id}")
    public boolean deleteRoleById(@ApiParam(required = true, value = "id", name = "id") @PathVariable Integer id) {
        return roleService.deleteRoleById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A page of roles was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get all roles by user", response = RoleRestController.class, httpMethod = "GET")
    @PreAuthorize("isAuthenticated()")
    @HasRoles(permissions = {"LIST_ROLE"})
    @GetMapping("userRoles")
    public List<Role> getRolesByUser(@ApiParam(required = true, value = "userId", name = "userId") @RequestParam Integer userId) {
        return roleService.getRolesByUser(userId);
    }
}
