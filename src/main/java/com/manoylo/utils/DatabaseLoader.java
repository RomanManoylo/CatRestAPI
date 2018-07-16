package com.manoylo.utils;

import com.manoylo.entity.User;
import com.manoylo.repository.UserRepository;
import com.manoylo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
        List<User> users = Arrays.asList(
                new User ("Vlad","password",new String[]{"ROLE_USER"}),
                new User ("Roman666","password",new String[]{"ROLE_USER"}),
                new User ("User","password",new String[]{"ROLE_USER","ROLE_ADMIN"})
        );
        userService.saveAll(users);
    }
}
