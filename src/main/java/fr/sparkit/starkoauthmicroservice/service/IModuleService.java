package fr.sparkit.starkoauthmicroservice.service;

import fr.sparkit.starkoauthmicroservice.model.Module;

import java.util.List;

public interface IModuleService {

    List<Module> findAll();
}
