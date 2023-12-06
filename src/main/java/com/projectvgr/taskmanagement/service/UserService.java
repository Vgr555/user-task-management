package com.projectvgr.taskmanagement.service;

import com.projectvgr.taskmanagement.dtos.UserDTO;
import com.projectvgr.taskmanagement.entities.TaskEntity;
import com.projectvgr.taskmanagement.entities.UserEntity;
import com.projectvgr.taskmanagement.enums.Role;
import com.projectvgr.taskmanagement.exceptionhandling.NoSuchUserException;
import com.projectvgr.taskmanagement.repository.TaskRepository;
import com.projectvgr.taskmanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO register(UserEntity userEntity) {
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity entity = userRepository.save(userEntity);
        return modelMapper.map(entity, UserDTO.class);
    }

    public List<UserDTO> getAllUsers(){
        List<UserEntity> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(UserEntity userEntity : users){
            userDTOS.add(modelMapper.map(userEntity, UserDTO.class));
        }
        return userDTOS;
    }

    public UserDTO getUserById(Integer id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()){
            throw new NoSuchUserException("User not found with id : "+id);
        }
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO updateUser(Integer id, UserEntity userEntity) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new NoSuchUserException("User not found with id : "+id);
        }
//        user.get().setId(userEntity.getId());
        user.get().setName(userEntity.getName());
        user.get().setEmail(userEntity.getEmail());
        user.get().setPassword(passwordEncoder.encode(userEntity.getPassword()));
        user.get().setRole(Role.USER);
        userRepository.save(user.get());
        return modelMapper.map(user, UserDTO.class);
    }

    public void deleteUser(Integer id, UserDTO user) {
        List<TaskEntity> tasks = taskRepository.findTasksByUserId(id);
        if(tasks != null && !tasks.isEmpty()){
            taskRepository.deleteTasksByUserId(id);
        }
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userRepository.delete(userEntity);
    }

    public UserDTO promoteUser(Integer id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()){
            throw new NoSuchUserException("User not found with id : "+id);
        }
        userEntity.get().setRole(Role.ADMIN);
        userRepository.save(userEntity.get());
        return modelMapper.map(userEntity, UserDTO.class);
    }
}
