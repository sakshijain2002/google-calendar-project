package com.task.taskservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private Time time;
    private String DayDate;
    private Boolean allDay;
    private Boolean availability;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
    private Boolean starredTask = false;
    private Integer userId;

}