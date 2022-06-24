package com.spring.shop.controllers;


import com.spring.shop.models.Role;
import com.spring.shop.models.User;
import com.spring.shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.Collections;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    //// [ НАЧАЛО ДЗ №12 ]
    // находим пользователя
    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        String login = principal.getName();
        User user = userRepository.findByUsername(login);
        model.addAttribute("user", user);
        return "user";
    }

    // находим пользователя и обновляем его данные
    @PostMapping("/user/update")
    public String updateUser(Principal principal, User userForm) {
        String login = principal.getName();
        User user = userRepository.findByUsername(login);

        user.setEmail(userForm.getEmail());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setRoles(userForm.getRoles());
        userRepository.save(user);
        return "user";
    }
    //// [ КОНЕЦ ДЗ №12 ]


    @GetMapping("/reg")
    public String reg(@RequestParam(name = "error", defaultValue = "", required = false) String error, Model model) {
        if (error.equals("username")){
            model.addAttribute("error", "Такой логин пользователя уже занят");
        }
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password) {
        if (userRepository.findByUsername(username) != null) {
            return "redirect:/reg?error=username";
        }

        password = passwordEncoder.encode(password);
        User user = new User(username, password, email, true, Collections.singleton(Role.ADMIN));
        userRepository.save(user);
        return "redirect:/login";
    }

}
