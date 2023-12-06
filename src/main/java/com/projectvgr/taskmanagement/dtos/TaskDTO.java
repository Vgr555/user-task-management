package com.projectvgr.taskmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDate deadline;
}
