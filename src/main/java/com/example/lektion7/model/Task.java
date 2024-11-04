package com.example.lektion7.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 32)
    private String title;

    @NotBlank
    @Size(max = 2000)
    private String description;

    // TODO - avoid including a USER in postman
    @ManyToOne
    @JoinColumn(name = "custom_user_id")
    //@NotNull
    private CustomUser customUser;

    public Task() {

    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId () {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }
}
