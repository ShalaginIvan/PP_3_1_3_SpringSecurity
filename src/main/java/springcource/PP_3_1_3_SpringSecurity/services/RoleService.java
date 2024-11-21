package springcource.PP_3_1_3_SpringSecurity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springcource.PP_3_1_3_SpringSecurity.model.Role;
import springcource.PP_3_1_3_SpringSecurity.repositories.RoleRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleAdmin() {
        return new Role(1L, "ROLE_ADMIN");
    }

    public Role getRoleUser() {
         return new Role(2L, "ROLE_USER");
    }

    public Set<Role> getAllRoles() {
        return Set.of(getRoleAdmin(), getRoleUser());
    }

    public void createRoles() {
        roleRepository.save(getRoleAdmin());
        roleRepository.save(getRoleUser());
    }
}
