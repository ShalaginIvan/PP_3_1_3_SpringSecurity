package springcource.PP_3_1_3_SpringSecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcource.PP_3_1_3_SpringSecurity.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

