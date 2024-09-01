package com.example.JournalApp.Controller;

import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/all-users")
    public ResponseEntity<?> getllUsers() {
        List<User> myusers = userService.getAll();
        if (!myusers.isEmpty() && myusers != null) {
            return new ResponseEntity<>(myusers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?>  createAdmin(@RequestBody User user){
        userService.createAdmin(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
}
