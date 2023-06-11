package web.userInit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import web.model.Role;
import web.model.User;
import web.services.UserServiceImpl;

import java.util.Set;

@Component
public class UserInit  implements ApplicationListener<ContextRefreshedEvent> {

    private final UserServiceImpl userService;

    @Autowired
    public UserInit(UserServiceImpl userService) {
        this.userService = userService;
    }

  @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        Set<Role> userRoles = Set.of(userRole);
        Set<Role> adminRoles = Set.of(adminRole);
        User user = new User("user", "user","user@mail.ru", userRoles);
        userService.saveUser(user);
        User admin = new User("admin", "admin","admin@mail.ru", adminRoles);
        userService.saveUser(admin);
    }
}


