package net.bmw.bmwphd.controller;


import net.bmw.bmwphd.domain.Result;
import net.bmw.bmwphd.domain.StatusCode;
import net.bmw.bmwphd.domain.User;
import net.bmw.bmwphd.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAll());
    }

    @GetMapping("/customers")
    public Result findAllCustomers(){
        return new Result(true, StatusCode.SUCCESS, "Find All Customers Success", service.findAllCustomers());
    }

    @GetMapping("/judges")
    public Result findAllJudges(){
        return new Result(true, StatusCode.SUCCESS, "Find All Judges Success", service.findAllJudges());
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        return new Result(true, StatusCode.SUCCESS, "Find By Id Success", service.findById(id));
    }

//    @GetMapping("/{username}")
//    public Result findByUsername(@PathVariable String username){
//        return new Result(true, StatusCode.SUCCESS, "Find By Username Success", service.findByUsername(username));
//    }

    @PostMapping
    public Result save(@RequestBody User user){
        service.save(user);
        return new Result(true, StatusCode.SUCCESS, "Save Success");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody User user, @PathVariable Integer id){
        service.update(id, user);
        return new Result(true, StatusCode.SUCCESS, "Update Success");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        service.deleteById(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
