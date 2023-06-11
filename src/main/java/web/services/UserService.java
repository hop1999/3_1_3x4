package web.services;

import org.springframework.stereotype.Component;
import web.model.User;

import java.util.List;

@Component
public interface UserService {

    List<User> allUsers();
    boolean saveUser(User user);
    void deleteUser(Long id);
    void update (User user, Long id);

    User findByUsername (String username);
}

