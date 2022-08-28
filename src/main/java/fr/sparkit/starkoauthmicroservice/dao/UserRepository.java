package fr.sparkit.starkoauthmicroservice.dao;

import fr.sparkit.starkoauthmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    List<User> findAllByIsDeletedFalse();


    @Transactional
    @Modifying
    @Query("update User u set u.language= :language where u.email = :email")
    int changeUserLanguage(@Param("email") String email, @Param("language") String language);

}
