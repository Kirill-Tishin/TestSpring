package com.curator.alert.Thread;

import com.curator.entity.Task;
import com.curator.entity.User;
import com.curator.service.TaskService;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.*;

public class MonitoringThread extends Thread {
    private List<Task> taskList; //Тут все задачи одного пользователя
    private User user;
    private NotificationThread notificationThread;
    private ArrayList<Task> tasksWorking;
    private TaskService taskService;

    public MonitoringThread(User user,TaskService taskService) {
        this.user = user;
        this.taskService = taskService;
        tasksWorking = new ArrayList<Task>();
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(5000); //Приостановка на 15 секунд //15000
            } catch (InterruptedException e) {
                break;
            }
            this.taskList = taskService.getListTaskByIdUser(user.getIdUser());
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                Date dateTask = task.getDatetask();
                Time timeTask = task.getTimetask();

                Calendar calendar = Calendar.getInstance();
                Date dateNow = calendar.getTime();
                Time timeNow = new Time(dateNow.getTime());

                if ((dateTask.getTime() <= dateNow.getTime()) && (timeTask.getTime() <= timeNow.getTime())) {
                    if (!tasksWorking.contains(task)) { //Проверка существрования элемента
                        tasksWorking.add(task);
                        notificationThread = new NotificationThread(user, task,taskService);
                        notificationThread.start();
                    } else {
                        System.out.println("Task выведена");
                        //Обновление ,если задача уже удалена, чтобы не получилось, случайно, листа огромного
                        // размера из задач, которые уже удалены или отложены,
                        // но в этом листе их старая версия уже осталась
                        ArrayList<Task> newTasks = new ArrayList<Task>();
                        for (int j = 0; j < taskList.size(); j++) {
                            for (int k = 0; k < tasksWorking.size(); k++) {
                                if (taskList.get(j) == tasksWorking.get(k)) {
                                    newTasks.add(tasksWorking.get(k));
                                }
                            }
                        }
                        tasksWorking = newTasks;
                    }
                }
            }
        }
    }
}