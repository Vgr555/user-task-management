package com.projectvgr.taskmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectvgr.taskmanagement.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    private String password;
    @JsonIgnore
    private Role role;
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TaskEntity> tasks;
}
