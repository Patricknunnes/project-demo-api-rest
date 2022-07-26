package br.com.pines.dev.controller;

import br.com.pines.dev.model.User;
import br.com.pines.dev.model.dto.UserForm;
import br.com.pines.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody @Valid UserForm userForm){
        User user = userForm.conversor(userForm);
        user.updatePasswordEncoder(passwordEncoder);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }


}
