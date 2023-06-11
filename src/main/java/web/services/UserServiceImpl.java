package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.model.User;

import web.repositories.UserRepository;

import javax.persistence.PersistenceException;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByUsername (String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    @Transactional
    public boolean saveUser(User user){
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userRepository.save(user);
        } catch (PersistenceException e){
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void update(User user, Long id) {
        String myPassword = user.getPassword();
        if (myPassword.isEmpty())
            user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        else
            user.setPassword(passwordEncoder.encode(myPassword));
        userRepository.save(user);
    }
}
