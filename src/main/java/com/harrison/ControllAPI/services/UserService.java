package com.harrison.ControllAPI.services;

import com.harrison.ControllAPI.model.User;
import com.harrison.ControllAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
        }

    public List<User> getAllUsers(){return userRepository.findAll();}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(
                () -> new RuntimeException("Username %s não encontrado!!".formatted(username)));
    }

    public User getUserById(Long userId){
        Optional<User> optional = userRepository.findById(userId);
        return optional.orElseThrow(
                () -> new RuntimeException("Usuário de ID %d não encontrado!!".formatted(userId)));
    }

    public User updateUserById(Long userId, User changes){
        userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Usuário de ID %d não encontrado!!".formatted(userId)));
        changes.setId(userId);
        return userRepository.save(changes);
    }

    public String removeUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Usuário de ID %d não encontrado!!".formatted(userId)));
        userRepository.deleteById(userId);
        return "Usuário %s foi removido!!".formatted(user.getUsername());
    }
}
