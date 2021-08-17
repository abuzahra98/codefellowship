package com.example.codefellowship.controller;

import com.example.codefellowship.modal.ApplicationUser;
import com.example.codefellowship.repositories.ApplicationUserRepository;
import com.example.codefellowship.modal.Post;
import com.example.codefellowship.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.Entity;
import java.security.Principal;
import java.util.List;

@Controller
public class ApplicationUserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @GetMapping("/")
    public String getHome(Principal p, Model model){
        try{
            model.addAttribute("userInfo",p.getName());
            model.addAttribute("hideWhenLogin","display: none");
            model.addAttribute("hideWhenLogout","display: auto");
            model.addAttribute("userId",applicationUserRepository.findByUsername(p.getName()).getId());
        }catch (NullPointerException e){

            model.addAttribute("userInfo","");
            model.addAttribute("hideWhenLogin","display: auto");
            model.addAttribute("hideWhenLogout","display: none");
        }
        return "home.html";
    }


    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }


    @GetMapping("/login")
    public String getSignInPage(){
        return "signin.html";
    }

    @PostMapping("/signup")
    public RedirectView signUp(@RequestParam(value="username") String username, @RequestParam(value="password") String password, @RequestParam(value="firstName") String firstName,@RequestParam(value="lastName") String lastName,@RequestParam(value="dateOfBirth") Integer dateOfBirth,@RequestParam(value="bio") String bio,@RequestParam(required=true,value="imageUrl") String imageUrl){
        ApplicationUser newUser = new ApplicationUser(username,bCryptPasswordEncoder.encode(password),firstName,lastName,dateOfBirth,bio,imageUrl);
        applicationUserRepository.save(newUser);
        return new RedirectView("/login");
    }



    @GetMapping("/user/{id}")
    public String getUser(Principal p, Model model, @PathVariable Long id){
            model.addAttribute("username",p.getName());
            model.addAttribute("UserInfo",applicationUserRepository.findById(id).get());

        return "user.html";
    }


    @GetMapping("/myprofile")
    public String getUserProfile(Principal p, Model model){
        try{
            // for the header
            model.addAttribute("userInfoe",p.getName());
            // for the body
            model.addAttribute("UserInfo",applicationUserRepository.findByUsername(p.getName()));
        }catch (NullPointerException e){
            model.addAttribute("userInfoe","");
            model.addAttribute("UserInfo",new ApplicationUser());
        }
        return "user.html";
    }

    @PostMapping("/myprofile")
    public RedirectView addPost(Principal p,@RequestParam String body){
        Post post = new Post(body,applicationUserRepository.findByUsername(p.getName()));
        postRepository.save(post);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/AllPost")
    public String getAllPost(Principal p, Model model){
        try{
            model.addAttribute("userInfoe",p.getName());
            model.addAttribute("UserInfo",applicationUserRepository.findByUsername(p.getName()));
        }catch (NullPointerException e){
            model.addAttribute("userInfoe","");
            model.addAttribute("UserInfo",new ApplicationUser());
        }
        return "feed.html";
    }

}
