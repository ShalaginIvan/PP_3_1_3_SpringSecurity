package springcource.PP_3_1_3_SpringSecurity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springcource.PP_3_1_3_SpringSecurity.model.Role;
import springcource.PP_3_1_3_SpringSecurity.repositories.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Set<Role> getAll() {
        List<Role> list = roleRepository.findAll();
        Set<Role> roles = new HashSet<>();
        roles.addAll(list);
        return roles;
    }

    // метод создания новой роли (если такая роль уже есть, то ее возвращаем)
    public Role getOrCreate(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

}
