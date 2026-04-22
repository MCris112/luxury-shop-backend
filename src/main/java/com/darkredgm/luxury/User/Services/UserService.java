package com.darkredgm.luxury.User.Services;

import com.darkredgm.luxury.User.Models.User;
import com.darkredgm.luxury.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> update(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            if (userDetails.getName() != null) user.setName(userDetails.getName());
            if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
            // Password update might need hashing, but for now we just set it if provided
            if (userDetails.getPassword() != null) user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        });
    }

    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByEmailOrCreate(String email, String name) {
        User user = this.userRepository.findByEmail(email);

        if (user != null)
            return user;

        user = new User();
        user.setName(name);
        user.setEmail(email);

        return userRepository.save(user);
    }
}
