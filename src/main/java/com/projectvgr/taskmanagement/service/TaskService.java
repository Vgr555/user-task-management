package com.projectvgr.taskmanagement.service;

import com.projectvgr.taskmanagement.dtos.TaskDTO;
import com.projectvgr.taskmanagement.entities.TaskEntity;
import com.projectvgr.taskmanagement.entities.UserEntity;
import com.projectvgr.taskmanagement.exceptionhandling.NoSuchTaskException;
import com.projectvgr.taskmanagement.exceptionhandling.NoSuchUserException;
import com.projectvgr.taskmanagement.repository.TaskRepository;
import com.projectvgr.taskmanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<TaskDTO> getAllTasks(Integer userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new NoSuchUserException("User with id "+userId+" not found");
        }
        List<TaskEntity> tasks = taskRepository.findTasksByUserId(userId);
//        List<TaskEntity> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for(TaskEntity task : tasks){
            taskDTOS.add(modelMapper.map(task, TaskDTO.class));
        }
        return taskDTOS;
    }

    public TaskEntity createTask(Integer userId, TaskEntity entity) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new NoSuchUserException("User with id "+userId+" not found");
        }
        entity.setUserEntity(user.get());
        return taskRepository.save(entity);
    }

    public TaskDTO getTaskById(Integer userId, Integer id) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new NoSuchUserException("User with id "+userId+" not found");
        }
        Optional<TaskEntity> task = taskRepository.findByIdAndUserId(userId, id);
        if(task.isEmpty()){
            throw new NoSuchTaskException("Task with id "+id+" not found!");
        }
        return modelMapper.map(task, TaskDTO.class);
    }

    public void deleteTask(TaskDTO dto) {
        TaskEntity entity = modelMapper.map(dto, TaskEntity.class);
        taskRepository.delete(entity);
    }

    public TaskDTO updateTask(Integer userId, Integer id, TaskEntity entity) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new NoSuchUserException("User with id "+userId+" not found");
        }
        Optional<TaskEntity> currentEntity = taskRepository.findById(id);
        if(currentEntity.isEmpty()) {
            throw new NoSuchTaskException("Task Not Found!");
        }
        currentEntity.get().setTitle(entity.getTitle());
        currentEntity.get().setDescription(entity.getDescription());
        currentEntity.get().setDeadline(entity.getDeadline());
        TaskEntity taskEntity = taskRepository.save(currentEntity.get());
        return modelMapper.map(taskEntity, TaskDTO.class);
    }
}
