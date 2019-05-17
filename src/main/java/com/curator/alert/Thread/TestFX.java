package com.curator.alert.Thread;

import com.curator.entity.Task;
import com.curator.entity.User;
import com.curator.service.TaskService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.*;

public class TestFX implements Initializable {
    private User user;
    private Task task;
    private TaskService taskService;

    public TestFX(User user, Task task,TaskService taskService) {
        this.task = task;
        this.user = user;
        this.taskService = taskService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Alert alert;

    @FXML
    void addAlter(String strOutput, String identification) throws SQLException, ParseException {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(identification);
        alert.setHeaderText(null);
        alert.setContentText(strOutput);

        ButtonType postpone = new ButtonType("Отложить");
        ButtonType remove = new ButtonType("Удалить");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(postpone, remove);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == postpone) {
         // Добавление 5-ти минтут к нынешнему времени
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendar.getTime());
            calendar.add(Calendar.MINUTE, 5);

            ArrayList<Task> arrayListNew = new ArrayList<Task>(user.getTaskEntities());
            //Обновили в бд
            Task taskEntityNew = new Task();
            taskEntityNew.setNameTask(task.getNameTask());
            taskEntityNew.setDescriptiontask(task.getDescriptiontask());

            Date date = calendar.getTime();
            java.sql.Date dateNew = new java.sql.Date(date.getTime());
            taskEntityNew.setDatetask(dateNew);

            taskEntityNew.setTimetask(new Time(calendar.getTimeInMillis()));
            taskEntityNew.setUser(user);
            taskEntityNew.setIdUser(user.getIdUser());
            taskService.deleteTask(task);
            taskService.addTaskAndUpdateTask(taskEntityNew);

            arrayListNew.remove(task); //Обновили у пользователя
            arrayListNew.add(taskEntityNew);
            user.setTaskEntities(arrayListNew);
        } else {
            ArrayList arrayListNew = new ArrayList<Task>(user.getTaskEntities());
            arrayListNew.remove(task);
            user.setTaskEntities(arrayListNew); //Удилили задачу из листа пользователя
            taskService.deleteTask(task); //Удилили задачу из бд
        }
    }
}