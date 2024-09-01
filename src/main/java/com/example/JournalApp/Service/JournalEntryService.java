package com.example.JournalApp.Service;

import com.example.JournalApp.Entity.JournalEntity;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repositary.JournalEntityRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


    @Autowired
    private  JournalEntityRepository journalEntityRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntity journalEntity, String userName)
    {
        try {
            User userDb = userService.findByUserName(userName);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntityRepository.save(journalEntity);
            userDb.getJournalEntries().add(saved);
            userService.saveEntry(userDb);
        }
        catch(Exception e){
            System.out.println(e);
            throw  new RuntimeException("An error occured saving the entry");
        }

    }

    public void saveEntry(JournalEntity journalEntity){
        journalEntity.setDate(LocalDateTime.now());
        journalEntityRepository.save(journalEntity);
    }

    public List<JournalEntity> getAll(){
        return journalEntityRepository.findAll();
    }


    public Optional<JournalEntity> findById(ObjectId id){
        return journalEntityRepository.findById(id);
    }
@Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed;
       try {
           User userInDb = userService.findByUserName(userName);
          removed = userInDb.getJournalEntries().removeIf(x -> x.getId().equals(id));
           if (removed) {
               userService.saveEntry(userInDb);
               journalEntityRepository.deleteById(id);
           }
           return removed;
       }catch (Exception e){
           System.out.println(e);
           throw  new RuntimeException("exception occured"+e);
       }
    }
}
