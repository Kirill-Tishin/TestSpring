package com.curator.controller;

import com.curator.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task")
    public String input() {
        return "index2";
    }

    //Проверка работы и примеры работы spring jpa repository
  /*  @GetMapping("/listTasksByIdUser")
    public String getListTasksByIdUser(){
        List<Task> tasks = taskService.getListTaskByIdUser(1);
        for(int i=0;i<tasks.size();i++){
            System.out.println(tasks.get(i).getNameTask());
        }
        return "index2";
    }

    @GetMapping("/listTaskIsNotNullDescription")
    public String getListTaskIsNotNullDescription(){
        List<Task> tasks = taskService.getTasksThisDescriptionIsNotNull();
        for(int i=0;i<tasks.size();i++){
            System.out.println(tasks.get(i).getNameTask());
        }
        return "index2";
    }

    @GetMapping("/listTaskInterval")
    public String getTestListTasksInInterval(){
        Calendar calendar = Calendar.getInstance();
        Time timeOne = new Time(calendar.getTimeInMillis());
        calendar.add(Calendar.MINUTE,30);
        Time timeTwo = new Time(calendar.getTimeInMillis());

        List<Task> tasks = taskService.getTasksInIntervalTime(timeOne,timeTwo);
        for(int i=0;i<tasks.size();i++){
            System.out.println(tasks.get(i).getNameTask());
        }
        if(tasks.size()==0){
            System.out.println("This interval time list tasks is null");
        }
        return "index2";
    }*/

    //Для работы приложения

}
