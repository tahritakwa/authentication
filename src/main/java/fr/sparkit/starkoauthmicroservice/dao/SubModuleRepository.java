package fr.sparkit.starkoauthmicroservice.dao;

import fr.sparkit.starkoauthmicroservice.model.SubModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubModuleRepository extends JpaRepository<SubModule, Integer> {
    Optional<SubModule> findByCode(String code);

    SubModule findByPermissions_Id(Integer id);
}
