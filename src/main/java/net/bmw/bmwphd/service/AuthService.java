package net.bmw.bmwphd.service;

import net.bmw.bmwphd.domain.MyUserPrincipal;
import net.bmw.bmwphd.domain.User;
import net.bmw.bmwphd.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * The service for handling the functions for Authentication.
 * </p>
 *
 * @author Chirayu Jain
 */
@Service
public class AuthService {
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * @param authentication - {@link Authentication} object with user details for which Login info is needed.
     * @return Map of Login User Info.
     */
    public Map<String, Object> createLoginInfo(Authentication authentication) {
        String token = jwtTokenProvider.createToken(authentication);
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();
        user.setPassword(""); // IMPORTANT, don't send password to the frontend!!!
        Map<String, Object> loginResultMap = new HashMap<>();
        loginResultMap.put("token", token);
        loginResultMap.put("userInfo", user);
        return loginResultMap;
    }
}
