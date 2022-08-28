package fr.sparkit.starkoauthmicroservice.dao;

import fr.sparkit.starkoauthmicroservice.model.DbSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbSettingsRepository extends JpaRepository<DbSettings, Integer> {
}
