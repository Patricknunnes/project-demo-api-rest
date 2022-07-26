package br.com.pines.dev.controller;

import br.com.pines.dev.model.User;
import br.com.pines.dev.model.dto.UserForm;
import br.com.pines.dev.repository.RoleRepository;
import br.com.pines.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping
    @Transactional
    public HttpStatus createUser(@RequestBody @Valid UserForm userForm){
        User user = userForm.conversor(userForm);
        if (userRepository.getReferenceByUsername(user.getUsername())!=null){
            return HttpStatus.BAD_REQUEST;
        } else {
            user.roleDefaultAdd(roleRepository);
            user.updatePasswordEncoder(passwordEncoder);
            userRepository.save(user);
            return HttpStatus.OK;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}")
    @Transactional
    public HttpStatus deleteUser(@PathVariable String username){
        if (userRepository.getReferenceByUsername(username)!=null){
            userRepository.deleteByUsername(username);
            return HttpStatus.OK;
        } return HttpStatus.BAD_REQUEST;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Transactional
    public List<User> listUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }


}
