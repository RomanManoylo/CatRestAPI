package com.manoylo.service;

import com.manoylo.entity.Cat;
import com.manoylo.entity.User;
import com.manoylo.repository.CatRepository;
import com.manoylo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CatRepository catRepository;


    private List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        saveUser(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public List<Cat> getAllCat(String name) {
        return userRepository.findByLogin(name).getCatList();
    }

    public void addCat(String name, Cat cat) {
        User user = userRepository.findByLogin(name);
        user.getCatList().add(cat);
        userRepository.save(user);
    }

    public void deleteAllCat(String name) {
        User user = userRepository.findByLogin(name);
        user.getCatList().clear();
        userRepository.save(user);
    }

    public boolean deleteCat(String name, long id) {
        User user = userRepository.findByLogin(name);
        Optional<Cat> cat = catRepository.findById(id);
        if (cat.isPresent()) {
            user.getCatList().remove(cat.get());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Optional<Cat> searchCat(long id) {
        return catRepository.findById(id);
    }
}
