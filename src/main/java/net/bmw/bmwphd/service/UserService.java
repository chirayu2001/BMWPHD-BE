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

/**
 * <p>
 * The service for handling the functions in {@link net.bmw.bmwphd.controller.UserControllerr}.
 * It returns the object returned from {@link UserDao} to {@link net.bmw.bmwphd.controller.UserController}.
 * </p>
 *
 * @author Chirayu Jain
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private UserDao userDao;
    private PasswordEncoder encoder;
//    private IdWorker idWorker;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
//        this.idWorker = idWorker;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * @return the list of all users.
     */
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * @param userId - id of the user needed to be retrieved.
     * @return {@link User} with the specified id.
     */
    public User findById(Integer userId) {
        return userDao.findById(userId).get();
    }

    /**
     * @param newUser - the new {@link User} object.
     */
    public void save(User newUser) {
        newUser.setPassword(encoder.encode(newUser.getPassword()));
//        newUser.setId(idWorker.nextId() + "");
        newUser.setActive(true);
        newUser.setUsername(newUser.getEmail());
        userDao.save(newUser);
    }

    /**
     * @param userId      - id of the user needed to be updated.
     * @param updatedUser - the new {@link User} object.
     */
    public void update(Integer userId, User updatedUser) {
        updatedUser.setId(userId);
        updatedUser.setPassword(updatedUser.getPassword());
        userDao.save(updatedUser);
    }

    /**
     * @param userId - id of the user that needs to be deleted.
     */
    public void deleteById(Integer userId) {
        User user = userDao.findById(userId).get();
        user.setActive(false);
        userDao.save(user);
    }

    /**
     * @return all Users with the Role = "Fan".
     */
    public List<User> findAllFans() {
        return userDao.findAllByRoleAndIsActive("Fan", true);
    }

    /**
     * @return all Users with the Role = "Judge".
     */
    public List<User> findAllJudges() {
        return userDao.findAllByRoleAndIsActive("Judge", true);
    }

    //for user form management so users can see their own forms
//    public List<Form> findFormsById(Integer id) {
//        User user = userDao.findById(id).get();
//        return user.getForms();
//    }

    /**
     * @param username - username of the user that needs to be loaded.
     * @return {@link UserDetails} object with the {@link User} with the specified username.
     */
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

    /**
     * @param username - username of the {@link User} that needs to be retrieved.
     * @return {@link User} with the specified username.
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
