package com.curator.repository;

import com.curator.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("select task from Task task where task.idUser=(:idUser)")
    List<Task> getTasksByIdUser(@Param("idUser") int idUser);

    @Query("select task from Task task where task.timetask>=(:oneTime) and task.timetask<=(:twoTime)")
    List<Task> getTasksInIntervalTime(@Param("oneTime")Time oneTime, @Param("twoTime")Time twoTime);

    @Query("select task from Task task where task.descriptiontask is not null")
    List<Task> getTasksThisDescriptionIsNotNull();
}