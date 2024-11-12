package springcource.PP_3_1_3_SpringSecurity.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import springcource.PP_3_1_3_SpringSecurity.model.Role;
import springcource.PP_3_1_3_SpringSecurity.model.User;
import springcource.PP_3_1_3_SpringSecurity.repositories.RoleRepository;

import java.util.*;


@Repository
@RequiredArgsConstructor
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    @SuppressWarnings("unchecked")
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from User where id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void initDataBaseForTest() {
        // Проверяем, существует ли роль ROLE_ADMIN в базе данных
        Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        if (roleAdmin.isEmpty()) {
            // Если роли нет, создаем её
            roleAdmin = Optional.of(new Role(1L, "ROLE_ADMIN"));
            roleRepository.save(roleAdmin.get());
        }

        // Проверяем, существует ли роль ROLE_USER в базе данных
        Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
        if (roleUser.isEmpty()) {
            // Если роли нет, создаем её
            roleUser = Optional.of(new Role(2L, "ROLE_USER"));
            roleRepository.save(roleUser.get());
        }

        // Проверяем, пуста ли таблица пользователей
        Long count = (Long) entityManager.createQuery("SELECT COUNT(u) FROM User u")
                .getSingleResult();
        if (count == 0) {
            // Если таблица пуста, то создаем 4 пользователей

            // админ
            User user1 = new User("Рустам", "Башаев", "kata@mail.ru", "1234");
            user1.setRoles(new HashSet<>(Arrays.asList(roleAdmin.get(), roleUser.get())));

            // user
            User user2 = new User("Mike", "Tyson", "mikeTyson@gmail.ru", "1234");
            user2.setRoles(Collections.singleton(roleUser.get()));

            // админ
            User user3 = new User("Jon", "Smith", "smith@list.ru", "1234");
            user3.setRoles(new HashSet<>(Arrays.asList(roleAdmin.get(), roleUser.get())));

            // user
            User user4 = new User("Олег", "Иванов", "олег-иванов@почта.ru", "1234");
            user4.setRoles(Collections.singleton(roleUser.get()));

            user1.setPassword(passwordEncoder.encode(user1.getPassword()));
            entityManager.persist(user1);
            user2.setPassword(passwordEncoder.encode(user2.getPassword()));
            entityManager.persist(user2);
            user3.setPassword(passwordEncoder.encode(user3.getPassword()));
            entityManager.persist(user3);
            user4.setPassword(passwordEncoder.encode(user4.getPassword()));
            entityManager.persist(user4);
        }
    }
}
