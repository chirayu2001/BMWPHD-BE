package net.bmw.bmwphd.controller;

import net.bmw.bmwphd.domain.Result;
import net.bmw.bmwphd.domain.StatusCode;
import net.bmw.bmwphd.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/token")
public class TokenController {
    private JwtTokenProvider jwtTokenProvider;

    public TokenController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/{token}")
    public Result validateToken(@PathVariable String token) {
        return new Result(true, StatusCode.SUCCESS, "Validating Token Success", jwtTokenProvider.isValid(token));
    }
}
