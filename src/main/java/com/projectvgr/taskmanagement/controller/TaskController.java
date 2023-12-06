package com.projectvgr.taskmanagement.controller;

import com.projectvgr.taskmanagement.dtos.TaskDTO;
import com.projectvgr.taskmanagement.entities.TaskEntity;
import com.projectvgr.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Welcome to task management";
    }

    @PostMapping("/{userId}/newTask")
    public TaskEntity createTask(@PathVariable Integer userId, @RequestBody TaskEntity entity) {
        return service.createTask(userId, entity);
    }

    @GetMapping("/{userId}/tasks")
    public List<TaskDTO> getAllTasks(@PathVariable Integer userId){
        return service.getAllTasks(userId);
    }

    @GetMapping("/{userId}/{id}")
    public TaskDTO getTaskById(@PathVariable Integer userId, @PathVariable Integer id) {
        return service.getTaskById(userId, id);
    }

    @PutMapping("/{userId}/{id}")
    public TaskDTO updateTask(@PathVariable Integer userId, @PathVariable Integer id, @RequestBody TaskEntity entity) {
        return service.updateTask(userId, id, entity);
    }

    @DeleteMapping("/{userId}/{id}")
    public String deleteTask(@PathVariable Integer userId, @PathVariable Integer id) {
        TaskDTO task = service.getTaskById(userId, id);
//        if(task == null) {
//            throw new NoSuchTaskException("Task Not Found!");
//        }
//        else {
//            service.deleteTask(task);
//            return "Task Deleted Successfully";
//        }
        service.deleteTask(task);
        return "Task Deleted Successfully";

    }
}
