package net.bmw.bmwphd.controller;


import net.bmw.bmwphd.domain.Result;
import net.bmw.bmwphd.domain.StatusCode;
import net.bmw.bmwphd.domain.User;
import net.bmw.bmwphd.service.HorseService;
import net.bmw.bmwphd.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     The entry point for api calls from the FrontEnd for User Queries.
 *     It wraps the object returned from {@link UserService} into {@link Result} and returns it to FrontEnd.
 *     <br>
 *     The base url for this api is: <strong>/users</strong>
 * </p>
 * @author Chirayu Jain
 *
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * @return the list of all users wrapped in <strong>Result</strong> object
     */
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAll());
    }

    /**
     * @return the list of all users with the role <strong>Fan</strong> wrapped in <strong>Result</strong> object
     */
    @GetMapping("/fans")
    public Result findAllFans(){
        return new Result(true, StatusCode.SUCCESS, "Find All Fans Success", service.findAllFans());
    }

    /**
     * @return the list of all users with the role <strong>Fan</strong> wrapped in <strong>Result</strong> object
     */
    @GetMapping("/judges")
    public Result findAllJudges(){
        return new Result(true, StatusCode.SUCCESS, "Find All Judges Success", service.findAllJudges());
    }
    /**
     * @param id - the id of the needed user
     * @return the user with the specified id wrapped in <strong>Result</strong> object
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        return new Result(true, StatusCode.SUCCESS, "Find By Id Success", service.findById(id));
    }

//    @GetMapping("/{username}")
//    public Result findByUsername(@PathVariable String username){
//        return new Result(true, StatusCode.SUCCESS, "Find By Username Success", service.findByUsername(username));
//    }
    /**
     * @param user - the User object in Request body  with attributes of User entity
     * @return the <strong>Result</strong> object mentioning if the Save was successful
     */
    @PostMapping
    public Result save(@RequestBody User user){
        service.save(user);
        return new Result(true, StatusCode.SUCCESS, "Save Success");
    }

    /**
     * @param id - the id of the user that needs to be updated
     * @return the <strong>Result</strong> object mentioning if the Update was successful
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody User user, @PathVariable Integer id){
        User current = (User) findById(id).getData();
        // current.set(changeField)
        user.setPassword(current.getPassword());
        service.update(id, user);
        /*

         */

        //current.set(newRole)

        return new Result(true, StatusCode.SUCCESS, "Update Success");
    }

    /**
     * @param id - the id of the user that needs to be deleted
     * @return the <strong>Result</strong> object mentioning if the Delete was successful
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        service.deleteById(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
