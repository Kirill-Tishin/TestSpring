package com.curator.controller;

import com.curator.alert.Signal.Signal;
import com.curator.entity.Task;
import com.curator.entity.User;
import com.curator.service.TaskService;
import com.curator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
//@RequestMapping(value = "/users")
public class UserController {
    private UserService userService;
    private TaskService taskService;
    private User user;
    private Task task;
    private Signal signal;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //Проверка работы и примеры работы spring jpa repository
    @GetMapping("/addUserTest")
    public String addUserTest() {
        User user = new User();
        user.setNameUser("UserTest12");
        userService.addUserAndUpdate(user);
        System.out.println("user added");
        return "index2";
    }

    @GetMapping("/update")
    public String updateUser() {
        User user = userService.getById(3);
        user.setNameUser("TestUpdateUser");
        userService.addUserAndUpdate(user);
        System.out.println("user update");
        return "index2";
    }

    @GetMapping("/userByNameTest")
    public String getUserByName() {
        User user = userService.getUserByNameUser("TestUpdateUser");
        System.out.println(user.getNameUser() + " " + user.getIdUser());
        return "index2";
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> print() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    //Проверка передачи на форму отсюда №1
//    @GetMapping(value = "/")
//    public String input (Model model) {
//        model.addAttribute("userTestPost",new User());
//        return "index2";
//    }
//
//    @PostMapping(value = "/")
//    public String checkInputNameUser(@ModelAttribute User user,Model model){
//        model.addAttribute("userTestPost",userService.getById(1));
//        return "index2";
//    }

    //Проверка получение данных с формы №2
//    @GetMapping(value = "/")
//    public String input () {
//        return "index2";
//    }
//
//    @PostMapping(value = "/")
//    public String checkInputNameUser(@RequestParam Map<String,String> params){
//        String nameUser = params.get("nameUser1");
//        System.out.println(nameUser);
//        return "index2";
//    }

    //    WORK
    @GetMapping(value = "/")
    public String input() {
        return "index2";
    }

    @PostMapping(value = "/")
    public String checkInputNameUser(@RequestParam Map<String, String> params, Model model) {
        String nameUser = params.get("nameUser1");
        this.user = userService.getUserByNameUser(nameUser);
        String html;
        if (user != null) {
            html = getListTask(model);

            //тут мы распознали пользователяи запускаем его поток уведомлений
            signal = new Signal(user,taskService);
            try {
                signal.startSignal(); //todo: как то потом эту шайтан машину необходимо остановить
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            //TOdo: передача месседжа на html
            System.out.println("Такого пользователя нет в системе");
            html = "index2";
        }
        return html;
    }

    public String getListTask(Model model) {
        ArrayList<Task> taskList = (ArrayList<Task>) taskService.getListTaskByIdUser(user.getIdUser());
        model.addAttribute("listTasks", taskList);
        return "listTask";
    }

    @GetMapping(value = "/UserListTask")
    public String getListTask(@ModelAttribute ArrayList<Task> taskList, Model model) {
        taskList = (ArrayList<Task>) taskService.getListTaskByIdUser(user.getIdUser());
        model.addAttribute("listTasks", taskList);
        return "listTask";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam Map<String, String> params) {
        int idTask = Integer.parseInt(params.get("inputIdTaskDelete"));
        taskService.deleteByIdTask(idTask);
        return "listTask"; //todo: как вызывать тут гет той страницы
    }

    @GetMapping("/openAddTask")
    public String openPageAddTask(Model model){
        Calendar calendarNow = Calendar.getInstance();
        Date dateNow = calendarNow.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        model.addAttribute("dateNow",dateFormat.format(dateNow));
        return "addTask";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam Map<String, String> params) {
        String html;
        String titleTask = params.get("titleTask");
        String dateString;
        Date date = null;
        String description;
        if (!titleTask.equals("")) {
            dateString = params.get("dateTimeTask");
            if (!dateString.equals("")){

                //todo: добавиь проверки на прошедшее время

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                try {
                    date = (Date) formatter.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(0);
                calendar.setTime(date);

                description=params.get("descriptionTask");

                Task task = new Task();
                task.setNameTask(titleTask);
                task.setDatetask(new java.sql.Date(calendar.getTime().getTime()));
                task.setTimetask(new java.sql.Time(calendar.getTime().getTime()));
                task.setDescriptiontask(description);
                task.setUser(user);
                task.setIdUser(user.getIdUser());
                taskService.addTaskAndUpdateTask(task);

                //TOdo: передача месседжа на html
                System.out.println("Задача добавлена");

                html = "listTask";
            }else{
                //TOdo: передача месседжа на html
                System.out.println("Введите время исполнения задачи");
                html = "addTask";
            }
        } else {
            //TOdo: передача месседжа на html
            System.out.println("Введите название задачи");
            html = "addTask";
        }
        return html;
    }

    @GetMapping("/openChangeTask")
    public String openChangeTask(@RequestParam Map<String, String> params,Model model){
        int idTask = Integer.parseInt(params.get("inputIdTaskChange"));
        this.task = taskService.getTask(idTask);
        model.addAttribute("titleTask",task.getNameTask());
        model.addAttribute("descriptionTask",task.getDescriptiontask());


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.setTime(task.getDatetask());

        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTimeInMillis(0);
        calendarTime.setTime(task.getTimetask());

        calendarTime.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendarTime.get(Calendar.HOUR_OF_DAY),calendarTime.get(Calendar.MINUTE));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        model.addAttribute("dateTask",dateFormat.format(calendarTime.getTime()));
        return "changeTask";
    }

    @PostMapping("/changeTask")
    public String changeTask(@RequestParam Map<String, String> params){
        String html;
        String titleTask = params.get("titleTask");
        String dateString;
        Date date = null;
        String description;
        if (!titleTask.equals("")) {
            dateString = params.get("dateTimeTask");
            if (!dateString.equals("")){

                //todo: добавиь проверки на прошедшее время

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                try {
                    date = (Date) formatter.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(0);
                calendar.setTime(date);

                description=params.get("descriptionTask");

                task.setNameTask(titleTask);
                task.setDatetask(new java.sql.Date(calendar.getTime().getTime()));
                task.setTimetask(new java.sql.Time(calendar.getTime().getTime()));
                task.setDescriptiontask(description);
                task.setUser(user);
                task.setIdUser(user.getIdUser());
                taskService.addTaskAndUpdateTask(task);

                //TOdo: передача месседжа на html
                System.out.println("Задача изменена");

                html = "listTask";
            }else{
                //TOdo: передача месседжа на html
                System.out.println("Введите время исполнения задачи");
                html = "changeTask";
            }
        } else {
            //TOdo: передача месседжа на html
            System.out.println("Введите название задачи");
            html = "changeTask";
        }
        return html;
    }
}