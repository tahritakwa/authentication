package fr.sparkit.starkoauthmicroservice.dao;

import fr.sparkit.starkoauthmicroservice.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
    Optional<Object> findByCode(String code);

    Module findBySubModules_Id(Integer id);
}
