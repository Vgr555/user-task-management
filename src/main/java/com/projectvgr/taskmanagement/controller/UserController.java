package com.projectvgr.taskmanagement.controller;

import com.projectvgr.taskmanagement.dtos.UserDTO;
import com.projectvgr.taskmanagement.entities.UserEntity;
import com.projectvgr.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserEntity userEntity){
        return userService.register(userEntity);
    }

    @GetMapping("/users")
    public List<UserDTO> allUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO userById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable Integer id, @RequestBody UserEntity userEntity){
        return userService.updateUser(id, userEntity);
    }

    @PutMapping("/promote/{id}")
    public UserDTO promoteUser(@PathVariable Integer id){
        return userService.promoteUser(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id){
        UserDTO user = userService.getUserById(id);
        userService.deleteUser(id, user);
        return "User with id : "+id+" deleted successfully";
    }

}
