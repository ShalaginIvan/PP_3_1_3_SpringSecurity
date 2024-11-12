package springcource.PP_3_1_3_SpringSecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springcource.PP_3_1_3_SpringSecurity.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    @Query("Select u from User u left join fetch u.roles where u.firstName=:userName")
    Optional<User> findByUserName (@Param("userName") String userName);
}
