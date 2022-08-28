package fr.sparkit.starkoauthmicroservice.service.impl;

import fr.sparkit.starkoauthmicroservice.dao.ModuleRepository;
import fr.sparkit.starkoauthmicroservice.model.Module;
import fr.sparkit.starkoauthmicroservice.service.IModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ModuleService implements IModuleService {

    private ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public List<Module> findAll() {
        return moduleRepository.findAll();
    }
}
