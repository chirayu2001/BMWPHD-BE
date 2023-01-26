package net.bmw.bmwphd.dao;

import net.bmw.bmwphd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    List<User> findAllByRoleAndIsActive(String role, Boolean isActive);

    User findByUsername(String username);
}
