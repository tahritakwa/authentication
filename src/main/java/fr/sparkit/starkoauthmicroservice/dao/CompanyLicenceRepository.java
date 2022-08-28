package fr.sparkit.starkoauthmicroservice.dao;

import fr.sparkit.starkoauthmicroservice.model.CompanyLicence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyLicenceRepository extends JpaRepository<CompanyLicence, Integer> {

    Optional<CompanyLicence> findByCompanyIdAndIsDeletedFalse(Integer idCompany);

}
