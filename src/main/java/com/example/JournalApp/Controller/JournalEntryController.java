package com.example.JournalApp.Controller;


import com.example.JournalApp.Entity.JournalEntity;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Service.JournalEntryService;
import com.example.JournalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
       User userdb= userService.findByUserName(userName);
        List<JournalEntity> j= userdb.getJournalEntries();
        if(j!=null &&!j.isEmpty()){
            return new ResponseEntity<>(j,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity journalEntity ){

      try {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          String userName=authentication.getName();
          journalEntryService.saveEntry(journalEntity,userName);
          return new ResponseEntity<>(journalEntity, HttpStatus.CREATED);
      }
      catch(Exception e){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }


    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntity> getJournalEntryById(@PathVariable ObjectId id){

        Optional<JournalEntity> journalEntity= journalEntryService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
       User userdb= userService.findByUserName(userName);
       List<JournalEntity> journalEntities= userdb.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!journalEntities.isEmpty()){
          Optional<JournalEntity> j= journalEntryService.findById(id);
          if(j.isPresent()){
              return new ResponseEntity<>(j.get(), HttpStatus.OK);
          }

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
         boolean b=journalEntryService.deleteById(myId,userName);
         if(b) {
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId,@RequestBody JournalEntity jnew){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
//        JournalEntity jold=journalEntryService.findById(myId).orElse(null);
//        if(jold!=null){
//            jold.setTitle(jnew.getTitle()!=null&&!jnew.getTitle().equals("")? jnew.getTitle(): jold.getTitle());
//            jold.setContent(jnew.getContent()!=null&&!jnew.getContent().equals("")?jnew.getContent(): jold.getContent());
//
//            journalEntryService.saveEntry(jold);
//            return new ResponseEntity<>(jold,HttpStatus.OK);
//        }
//
//
//
//           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User userdb= userService.findByUserName(userName);
        List<JournalEntity> journalEntities= userdb.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!journalEntities.isEmpty()) {
            Optional<JournalEntity> j = journalEntryService.findById(myId);
            if (j.isPresent()) {
                JournalEntity jold = j.get();
                if (jold != null) {
                    jold.setTitle(jnew.getTitle() != null && !jnew.getTitle().equals("") ? jnew.getTitle() : jold.getTitle());
                    jold.setContent(jnew.getContent() != null && !jnew.getContent().equals("") ? jnew.getContent() : jold.getContent());

                    journalEntryService.saveEntry(jold);
                    return new ResponseEntity<>(jold, HttpStatus.OK);
                }

            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}


