package net.bmw.bmwphd.controller;

import net.bmw.bmwphd.domain.Result;
import net.bmw.bmwphd.domain.StatusCode;
import net.bmw.bmwphd.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * The end point for login.
 * It wraps the object returned from {@link AuthService} into {@link Result} and returns it to FrontEnd.
 * <br>
 * The base url for this api is: <strong>/login</strong>
 * </p>
 *
 * @author Chirayu Jain
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * @param authentication - username and password as Basic Auth Authentication format
     * @return the user info for the logged-in user. It is wrapped in Result object
     * @throws Exception - throws exception if Authentication fails
     */
    @PostMapping("/login")
    public Result login(Authentication authentication) throws Exception {
        System.out.println(authentication.getAuthorities());
        return new Result(true, StatusCode.SUCCESS, "JWT Token and User Info", authService.createLoginInfo(authentication));
    }
}
