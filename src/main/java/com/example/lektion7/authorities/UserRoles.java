package com.example.lektion7.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.example.lektion7.authorities.UserPermission.*;

public enum UserRoles {

    GUEST(List.of(GET.getPermission())),
    USER(List.of(GET.getPermission(), POST.getPermission())),
    ADMIN(List.of(GET.getPermission(), POST.getPermission(), DELETE.getPermission()));

    private final List<String> permission;

    UserRoles(List<String> permission) {
        this.permission = permission;
    }

    public List<String> getPermission () {
        return permission;
    }

    public List<SimpleGrantedAuthority> getAuthorities () {

        // TODO - Roles
        // TODO - Permissions
        // TODO - Create List Authority (concat both roles & perms)

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>(
//                getPermission().stream().map(index -> new SimpleGrantedAuthority(index)).toList()
        );

        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + this.name())); // Springs requirement to Authority role

        simpleGrantedAuthorityList.addAll(getPermission().stream().map(SimpleGrantedAuthority::new).toList());

        return simpleGrantedAuthorityList;

    }


//    GUEST("GET"),
//    USER("GET_POST"),
//    ADMIN("GET_POST_PUT_DELETE");


//    final String permissions;
//
//    Roles(String permissions) {
//        this.permissions = permissions;
//    }
//
//    public String getPermissions () {
//        return permissions;
//    }
//
//
//    //TODO - Generates SimpleGrantedAuthorities as an Array(list)
//    public List<GrantedAuthority> splitPermissions () {
//
//        String[] permissionsArray = permissions.split("_");
//
//        return Arrays.stream(permissionsArray)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//    }


}
