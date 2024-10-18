package com.example.lektion7;

import com.example.lektion7.authorities.UserRoles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class Lektion7Application {

	public static void main(String[] args) {
		SpringApplication.run(Lektion7Application.class, args);

		System.out.println("Admin" +
				UserRoles.ADMIN.getPermission()
		);

		System.out.println("User" +
				UserRoles.USER.getPermission()
		);

		System.out.println("Guest" + UserRoles.GUEST.getPermission());

		for (UserRoles userRoles : UserRoles.values()) {
			System.out.println("for each");
			System.out.println(userRoles);
			System.out.println(userRoles.getPermission());
		}

		System.out.println("---GetAuthorities---");
		System.out.println(UserRoles.ADMIN.name().toString()); // Should not return ROLE_ + name
		System.out.println(
				UserRoles.ADMIN.getAuthorities()
		);

	}

}