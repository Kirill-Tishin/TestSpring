package com.curator.alert.Thread;

import com.curator.entity.Task;
import com.curator.entity.User;
import com.curator.service.TaskService;
import javafx.application.Platform;

import java.sql.SQLException;
import java.text.ParseException;

public class NotificationThread extends Thread {
    private TestFX testFX;
    private Task task;
    private TaskService taskService;

    public NotificationThread(User user, Task task, TaskService taskService) {
        this.task = task;
        this.taskService = taskService;
        testFX = new TestFX(user, task, taskService);
    }

    public void run() {
        System.out.println("Пришло время выполнения задачи " + task.getIdTask() + " " + task.getNameTask() + "\n Date Task " + task.getDatetask() + " Time Task " + task.getTimetask());


        /*Platform.runLater(new Thread() {
            @Override
            public void run() {
                try {
                    testFX.addAlter("Пришло время выполнения задачи " + task.getIdTask() + " " + task.getNameTask() + "\n Date PojoClass.Task " + task.getDatetask() + " Time Task " + task.getTimetask(), "Задача " + task.getNameTask());
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });*/
    }
}
