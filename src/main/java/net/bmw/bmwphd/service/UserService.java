package net.bmw.bmwphd.service;

import net.bmw.bmwphd.dao.UserDao;
import net.bmw.bmwphd.domain.MyUserPrincipal;
import net.bmw.bmwphd.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class UserService implements UserDetailsService {

    private UserDao userDao;
    private PasswordEncoder encoder;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public User findById(Integer userId) {
        return userDao.findById(userId).get();
    }

    public void save(User newUser) {
        newUser.setPassword(encoder.encode(newUser.getPassword()));
//        newUser.setId((int) idWorker.nextId());
        newUser.setActive(true);
        newUser.setUsername(newUser.getEmail());
        userDao.save(newUser);
    }

    public void update(Integer userId, User updatedUser) {
        updatedUser.setId(userId);
        userDao.save(updatedUser);
    }

    public void deleteById(Integer userId) {
        User user = userDao.findById(userId).get();
        user.setActive(false);
        userDao.save(user);
    }

    //for spirit director user management
    public List<User> findAllCustomers(){
        return userDao.findAllByRoleAndIsActive("Customer", true);
    }

    //to see all judges
    public List<User> findAllJudges(){
        return userDao.findAllByRoleAndIsActive("Judge", true);
    }

    //for user form management so users can see their own forms
//    public List<Form> findFormsById(Integer id) {
//        User user = userDao.findById(id).get();
//        return user.getForms();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Step 1, we need to find this user from DB
        User user = userDao.findByUsername(username);
        // Step 2, if the user does exist
        if (user == null) {
            throw new UsernameNotFoundException("username " + username + " is not found");
        }
        // Otherwise, wrap the returned user instance in a MyUserPrincipal instance
        return new MyUserPrincipal(user); // return the principal
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
