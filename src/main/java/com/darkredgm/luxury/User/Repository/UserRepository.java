package com.darkredgm.luxury.User.Repository;

import com.darkredgm.luxury.User.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmailAndPassword(String email, String password);
}
