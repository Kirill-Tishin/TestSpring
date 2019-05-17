package com.curator.entity;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "public", catalog = "TaskManagerDB")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;
    @Basic
    @Column(name = "name_user")
    private String nameUser;
    @OneToMany(mappedBy = "user")
    private Collection<Task> taskEntities;
}
