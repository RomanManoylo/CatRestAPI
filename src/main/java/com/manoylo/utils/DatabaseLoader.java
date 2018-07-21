package com.manoylo.utils;

import com.manoylo.entity.Cat;
import com.manoylo.entity.User;
import com.manoylo.repository.CatRepository;
import com.manoylo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private UserService userService;

    @Autowired
    public DatabaseLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Vlad", "password", new String[]{"ROLE_USER"}));
        users.add(new User("Roman666", "password", new String[]{"ROLE_USER"}));

        User user = new User("User", "password", new String[]{"ROLE_USER", "ROLE_ADMIN"});
        Cat tom = new Cat("Tom", "", "5 age");


        user.getCatList().add(tom);
        users.add(user);
        userService.saveAll(users);
    }
}
