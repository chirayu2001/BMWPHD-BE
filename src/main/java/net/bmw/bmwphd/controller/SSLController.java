package net.bmw.bmwphd.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin (maxAge = 3600)
@RestController
@RequestMapping ("/.well-known")
public class SSLController {
    @GetMapping ("/pki-validation/D78650009984419DF3B45AD5A5032D10.txt")
    public String httpValidation () {
        return "14AA3C4314E58E4AFA522E9AF2EA42C7F0ABB317D77E6A62DC6F2D491FAAF3D4 comodoca.com 63cb257331977";
    }

}
