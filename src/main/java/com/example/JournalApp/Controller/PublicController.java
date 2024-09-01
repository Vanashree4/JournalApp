package com.example.JournalApp.Controller;


import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

@Autowired
private UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveEntryNew(user);
    }

    @GetMapping("/health-check")
    public String myHealth(){
        return "ok";
    }
}
