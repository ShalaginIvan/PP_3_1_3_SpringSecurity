package springcource.PP_3_1_3_SpringSecurity.dao;

import springcource.PP_3_1_3_SpringSecurity.model.User;

import java.util.List;

public interface UserDao {
    public User getById(Long id);

    public List<User> getAll();

    public void save (User user);

    public void update(User user);

    public void delete(Long id);

    public void initDataBaseForTest();
}
