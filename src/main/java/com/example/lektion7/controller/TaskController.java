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

    @GetMapping("/editTask")
    public String saveTask (@RequestParam Long taskId, Model model) {
        Task task = taskRepository.findById(taskId).get();


       // model.addAttribute("taskId", task.getId()).addAttribute("taskDescription", task.getDescription()).addAttribute("taskTitle", task.getTitle());

        model.addAttribute("task", task);

        return "task";
    }
    @PostMapping("/editTask")
    public String saveTask (
                            @RequestParam("taskId") Long taskId,
                            //@RequestParam("title") String title,
                            //@RequestParam("description") String description
                            @ModelAttribute("task") Task task
    ) {

        Task task1 = taskRepository.findById(taskId).orElse(null);
        System.out.println("task id: " + task.getId()); //Why is it null
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

}
