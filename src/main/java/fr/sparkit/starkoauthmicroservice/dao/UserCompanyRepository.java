package fr.sparkit.starkoauthmicroservice.dao;

import fr.sparkit.starkoauthmicroservice.model.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompany, Integer> {
    List<UserCompany> findByUser_IdAndIsDeletedFalse(Integer id);

    List<UserCompany> findByUser_EmailAndIsDeletedFalse(String email);

    Optional<UserCompany> findByUser_IdAndCompany_IdAndIsDeletedFalse(Integer userId, Integer companyId);
}
