package com.curator.alert.Signal;

import com.curator.alert.Thread.MonitoringThread;
import com.curator.entity.User;
import com.curator.service.TaskService;

import java.sql.SQLException;

public class Signal {
    private User userEntity;
    private MonitoringThread monitoringThread;
    private TaskService taskService;

    public Signal(User userEntity,TaskService taskService) {
        this.userEntity = userEntity;
        this.taskService = taskService;
    }

    //Запуск потока с уведомлениями
    public void startSignal() throws SQLException {
        monitoringThread = new MonitoringThread(userEntity,taskService);
        monitoringThread.start();
    }

    //Остановка потока с уведомлениями
    public void stopSignal() {
        if (!monitoringThread.currentThread().isInterrupted()) {
            monitoringThread.interrupt(); //Остановка потока
            System.out.println("Поток уведомлений остановлен");
        } else {
            System.out.println("Поток уведомлений еще не запущен");
        }
    }
}