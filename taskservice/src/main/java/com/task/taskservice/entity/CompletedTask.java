package com.task.taskservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompletedTask {

    @Id

    private Long id;

    private String title;
    private String description;
    private String time;
    private String date;
    private Boolean allDay;
    private Boolean completed = true;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
    private Boolean starredTask = false;
    private Integer userId;
    private Long repeatTypeId;

}
