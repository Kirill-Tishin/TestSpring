package com.curator.service;

import com.curator.entity.Task;
import com.curator.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTaskAndUpdateTask(Task task){
        taskRepository.save(task);
    }

    public void deleteTask(Task task){
        taskRepository.delete(task);
    }

    @Transactional
    @Modifying
    public void deleteByIdTask(int idTask){
        taskRepository.deleteById(idTask);
    }

    public Task getTask(int idTask) {
        return taskRepository.findById(idTask).get();
    }

    public List<Task> getListTaskByIdUser(int idUser) {
        return taskRepository.getTasksByIdUser(idUser);
    }

    public void printAll() {
        System.out.println(taskRepository.findAll());
    }

    public List<Task> getTasksInIntervalTime(Time timeOne, Time timeTwo){
        return taskRepository.getTasksInIntervalTime(timeOne,timeTwo);
    }

    public List<Task> getTasksThisDescriptionIsNotNull(){
        return taskRepository.getTasksThisDescriptionIsNotNull();
    }
}