package com.example.lektion7.controller;

import com.example.lektion7.model.CustomUser;
import com.example.lektion7.model.Task;
import com.example.lektion7.repository.TaskRepository;
import com.example.lektion7.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController (TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;

    }

    @GetMapping("/alltasks")
    public String showTasksPage (Task task, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        //Enforce the user isPresent()
        CustomUser currentUser = userRepository.findByUsername(username).get();

        List<Task> usersTasks = currentUser.getTasks();

        model.addAttribute("userTasks", usersTasks);

        return "my-tasks";

    }

    // TODO - not in use..
    @PostMapping("/task")
    public String registerUser (@Valid Task task, BindingResult result) {

        if (result.hasErrors()) {
            return "task";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        CustomUser currentUser = userRepository.findByUsername(username).get();

        task.setCustomUser(currentUser);

        taskRepository.save(task);

        return "redirect:/";

    }

    // TODO - other users can access others tasks due to @RequestParam taskId..
    // Todo - even without @RequestParam, it still looks like this http://localhost:8080/editTask?taskId1=1
    @GetMapping("/editTask")
    public String saveTask (//@RequestParam Long taskId,
                            @ModelAttribute("taskId1") Long taskId,
                            Model model) {
        Task task = taskRepository.findById(taskId).get();


       // model.addAttribute("taskId", task.getId()).addAttribute("taskDescription", task.getDescription()).addAttribute("taskTitle", task.getTitle());

        model.addAttribute("task", task);

        return "task";
    }
    @PostMapping("/editTask")
    public String saveTask (
                            //@RequestParam("taskId") Long taskId, // Alternative approach, if no setter for id in Task
                            //@RequestParam("title") String title,
                            //@RequestParam("description") String description
                            @ModelAttribute("task") Task task
    ) {



        Task task1 = taskRepository.findById(task.getId()).orElse(null);
        System.out.println("task id: " + task.getId()); //Why is it null, because it had no setter..
        System.out.println("task title: " + task.getTitle()); // but these are not..
        System.out.println("task desc: " + task.getDescription());

        if (task1 == null) {

            return "error-page";
        }

        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        taskRepository.save(task1);

        return "redirect:/alltasks";

    }

    @GetMapping("/newTask")
    public String makeTask (Model model) {

        model.addAttribute("task", new Task());

        return "new-task";
    }

    // TODO - needs more error handling, validation etc
    @PostMapping("/newTask")
    public String createTask (@ModelAttribute("task") Task task) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Optional<CustomUser> currentUser = userRepository.findByUsername(username);

        Task newTask = new Task();

        newTask.setDescription(task.getDescription());
        newTask.setTitle(task.getTitle());
        newTask.setCustomUser(currentUser.get());

        taskRepository.save(newTask);


        return "redirect:/alltasks";
    }

}
