package com.example.lektion7.model;

import com.example.lektion7.authorities.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles userRole;

    //private Collection<? extends GrantedAuthority> authorities;  //SQL databases do not support GrantedAuthority

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialNonExpired;
    private boolean isEnabled;

    public CustomUser() {

    }

    public CustomUser(String username, String password, UserRoles userRole, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialNonExpired, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialNonExpired = isCredentialNonExpired;
        this.isEnabled = isEnabled;
    }

    public Long getId () {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // TODO - Returns just the ENUM Name (ADMIN), we already have authorities as a list
    // Authorities include : Role and permission: ["ROLE_ADMIN", "GET", "DELETE"]
    //@JsonIgnore
    public List<SimpleGrantedAuthority> getAuthorities () {
        return userRole.getAuthorities();
    }
    // Permissions include ["GET", "DELETE"]
    //@JsonIgnore //userRepository.save() will print out these details otherwise
    public List<String> getPermissions () {
        return userRole.getPermission();
    }

    //Role include: ADMIN (UserRoles.name())
    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }



//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialNonExpired() {
        return isCredentialNonExpired;
    }

    public void setCredentialNonExpired(boolean credentialNonExpired) {
        isCredentialNonExpired = credentialNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
