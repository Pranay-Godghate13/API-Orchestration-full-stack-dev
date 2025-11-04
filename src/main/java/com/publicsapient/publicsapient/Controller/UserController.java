package com.publicsapient.publicsapient.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import com.publicsapient.publicsapient.Model.APIUser;
import com.publicsapient.publicsapient.Payload.APIUserDTO;
import com.publicsapient.publicsapient.Payload.ResponseDTO;
import com.publicsapient.publicsapient.Service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;






@RestController
// @RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserServiceImpl userServiceImpl;

   
    @PostMapping("/user/loadData")
    public ResponseEntity<String> loadData() {
        try
        {
            String message=userServiceImpl.loadData();
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
        catch(DataIntegrityViolationException e)
        {
            String message="Invalid data";
            return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
        }
        catch(Exception e)
        {
            String message="Data load failed";
            return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    @GetMapping("/user/id/{id}")
    public ResponseEntity<APIUserDTO> getUserById(@PathVariable Long id) {
        try
        {
            APIUserDTO user=userServiceImpl.findUserById(id);
            return new ResponseEntity<APIUserDTO>(user, HttpStatus.FOUND);
        }
        catch(ResponseStatusException e)
        {
            return new ResponseEntity<>(e.getStatusCode());
        }
           
    }
    
    // @GetMapping("/user")
    // public List<APIUser> getAllUsers() {
    //     List<APIUser> users=userServiceImpl.getAllUsers();
        
    //     return users;
    // }
    
    @GetMapping("/user/email/{email}")
    public ResponseEntity<APIUserDTO> getUserByEmail(@PathVariable String email) {
        try
        {
            APIUserDTO user=userServiceImpl.findUserByEmail(email);
            return new ResponseEntity<APIUserDTO>(user, HttpStatus.FOUND);
        }
        catch(ResponseStatusException e)
        {
            return new ResponseEntity<>(e.getStatusCode());
        }
        
    }
    

    @GetMapping("/user/keyword/{keyword}")
    public ResponseEntity<ResponseDTO> getUsersByKeyword(@PathVariable String keyword) {
        try
        {
            ResponseDTO users=userServiceImpl.findByKeyword(keyword);
            return new ResponseEntity<ResponseDTO>(users, HttpStatus.FOUND);
        }
        catch(ResponseStatusException e)
        {
            return new ResponseEntity<>(e.getStatusCode());
        }
        
    }
    

    
    

   


        
    
}
