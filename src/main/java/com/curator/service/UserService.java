package com.curator.service;

import com.curator.entity.User;
import com.curator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUserAndUpdate(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUserById(int idUser) {
        userRepository.deleteById(idUser);
    }

    public User getById(int idUser) {
        return userRepository.findById(idUser).get();
    }

    public User getUserByNameUser(String nameUser) {
        return userRepository.getUserByNameUser(nameUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}