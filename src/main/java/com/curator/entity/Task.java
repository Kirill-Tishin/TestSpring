package com.curator.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "task", schema = "public", catalog = "TaskManagerDB")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private int idTask;
    @Basic
    @Column(name = "name_task")
    private String nameTask;
    @Basic
    @Column(name = "descriptiontask")
    private String descriptiontask;
    @Basic
    @Column(name = "datetask")
    private Date datetask;
    @Basic
    @Column(name = "timetask")
    private Time timetask;
    @Basic
    @Column(name = "id_user")
    private int idUser;
    @ManyToOne//(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false, insertable = false, updatable = false)
    private User user;
}
