package springcource.PP_3_1_3_SpringSecurity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcource.PP_3_1_3_SpringSecurity.dao.UserDao;
import springcource.PP_3_1_3_SpringSecurity.model.User;
import springcource.PP_3_1_3_SpringSecurity.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    @Transactional
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    public boolean save(User user) {
        Optional<User> userFromBD = userRepository.findByUserName(user.getFirstName());
        if (!userFromBD.isEmpty()) {
            return false;
        }
        userDao.save(user);
        return true;
    }

    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional
    public void initDataBaseForTest() {
        userDao.initDataBaseForTest();
    }
}
