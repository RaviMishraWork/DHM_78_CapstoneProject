package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User updateUser(int id, User p_user) {
        return userRepository.findById(id).map(
                user->{
                    user.setName(p_user.getName());
                    user.setEmail(p_user.getEmail());
                    return userRepository.save(user);
                }
        ).orElseThrow(()->new RuntimeException("User Not Found"));
    }
}
