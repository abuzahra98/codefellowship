package com.example.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @GetMapping("/login")
    public String getSignInPage(){
        return "signin.html";
    }

    @PostMapping("/signup")
    public RedirectView signUp(@RequestParam(value="username") String username, @RequestParam(value="password") String password){
        ApplicationUser newUser = new ApplicationUser(username,bCryptPasswordEncoder.encode(password));
        applicationUserRepository.save(newUser);
        return new RedirectView("/login");
    }


}
