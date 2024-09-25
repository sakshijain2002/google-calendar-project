package com.task.taskservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String time;
    private Long day;
    private Boolean allDay;
    private Boolean completed = false;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
    private Boolean starredTask = false;
    private LocalDateTime starredDate;

    private Integer userId;

    private Long repeatTypeId;

    private String email;
    private String label;


    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StarredTask> starredTasks =new ArrayList<>();


}