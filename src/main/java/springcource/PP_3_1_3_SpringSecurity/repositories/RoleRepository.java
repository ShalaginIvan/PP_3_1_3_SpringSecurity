package springcource.PP_3_1_3_SpringSecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springcource.PP_3_1_3_SpringSecurity.model.Role;
import springcource.PP_3_1_3_SpringSecurity.model.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName (@Param("name") String name);
    Optional<Set<Role>> findAllByNameIn(Set<String> names);
}

