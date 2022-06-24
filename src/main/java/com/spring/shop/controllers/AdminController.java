package com.spring.shop.controllers;

import com.spring.shop.models.Item;
import com.spring.shop.models.User;
import com.spring.shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/user-{id}")
    public String userReviews(@PathVariable(value = "id") long userId, Model model) {
        User user = userRepository.findById(userId).orElse(new User());
        Iterable<Item> items = user.getItems();
        model.addAttribute("items", items);
        return "user-items";
    }


}
