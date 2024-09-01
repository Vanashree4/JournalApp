package com.example.JournalApp.Controller;


import com.example.JournalApp.Entity.JournalEntity;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repositary.UserRepository;
import com.example.JournalApp.Service.JournalEntryService;
import com.example.JournalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
    UserService userService;
   @Autowired
    UserRepository userRepository;






   @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName=authentication.getName();
       User userindb=userService.findByUserName(userName);
       if(userindb!=null){
           userindb.setUserName(user.getUserName());
           userindb.setPassword(user.getPassword());
           userService.saveEntryNew(userindb);
       }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}


