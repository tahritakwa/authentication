package fr.sparkit.starkoauthmicroservice.controller;

import fr.sparkit.starkoauthmicroservice.constants.SwaggerApiMessageConstant;
import fr.sparkit.starkoauthmicroservice.model.Module;
import fr.sparkit.starkoauthmicroservice.service.IModuleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth/module")
public class ModuleController {
    private final IModuleService moduleService;

    @Autowired
    public ModuleController(IModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An array of modules was returned successfully", response = Exception.class),
            @ApiResponse(code = 404, message = SwaggerApiMessageConstant.NOT_FOUND, response = Exception.class),
            @ApiResponse(code = 500, message = SwaggerApiMessageConstant.ERROR_OCCURRED, response = Exception.class)})
    @ApiOperation(value = "Get all modules", response = ModuleController.class, httpMethod = "GET")
    @GetMapping("/modules")
    public List<Module> getModules() {
        return moduleService.findAll();
    }
}
