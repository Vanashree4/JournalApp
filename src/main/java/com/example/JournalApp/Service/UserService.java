package com.example.JournalApp.Service;

import com.example.JournalApp.Entity.JournalEntity;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repositary.JournalEntityRepository;
import com.example.JournalApp.Repositary.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Data
@Slf4j
public class UserService {


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

   // private static final Logger logger= LoggerFactory.getLogger(UserService.class);

//    public void saveEntry(User user)
//    {
//        userRepository.save(user);
//    }

    public boolean saveEntryNew(User user)
    {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e){

            log.error("error occured: {}",user.getUserName());
            log.info("hahaha");
//            logger.warn("hahaha");
         log.debug("debug");
   return false;
        }


    }
public void saveEntry(User user){
    userRepository.save(user);
}
    public List<User> getAll(){
        return userRepository.findAll();
    }


    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

//    public void deleteById(ObjectId id){
//        userRepository.deleteById(id);
//    }


    public User findByUserName(String username){
       return  userRepository.findByUserName(username);
    }

    public void createAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
}
