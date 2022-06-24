package com.spring.shop.controllers;

import com.spring.shop.models.Item;
import com.spring.shop.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String main(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/about-us")
    public String about(@RequestParam(name = "userName", required = false, defaultValue = "World")
                            String username, Model model) {
        model.addAttribute("name", username);
        return "about";
    }
}
