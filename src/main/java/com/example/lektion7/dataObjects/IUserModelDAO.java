package com.example.lektion7.dataObjects;

import com.example.lektion7.model.CustomUser;

import java.util.Collection;

public interface IUserModelDAO {

    CustomUser findUser (String username);
    Collection<CustomUser> findAllUsers ();
    void save (CustomUser customUser);


}
