package br.com.pines.dev.controller;

import br.com.pines.dev.model.Role;
import br.com.pines.dev.model.User;
import br.com.pines.dev.model.dto.UserForm;
import br.com.pines.dev.repository.RoleRepository;
import br.com.pines.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody @Valid UserForm userForm){
        User user = userForm.conversor(userForm);
        Optional<User> userOptional = userRepository.findById(user.getUsername());
        if (userOptional.isPresent()){
            return ResponseEntity.badRequest().build();
        } else {
            user.roleDefaultAdd(roleRepository);
            user.updatePasswordEncoder(passwordEncoder);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping("/admin")
    @Transactional
    public ResponseEntity<User> createUserAdmin(@RequestBody @Valid UserForm userForm){
        User user = userForm.conversor(userForm);
        Optional<User> userOptional = userRepository.findById(user.getUsername());
        if (userOptional.isPresent()){
            return ResponseEntity.badRequest().build();
        } else {
            user.updateAdmin(roleRepository);
            user.updatePasswordEncoder(passwordEncoder);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity<User> deleteUser(@PathVariable String username){
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            userRepository.deleteById(username);
            return ResponseEntity.ok().build();
        } return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Transactional
    public Page<User> listUsers(@PageableDefault(page = 0, size = 15,
            sort = "username", direction = Sort.Direction.ASC) Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{username}")
    @Transactional
    public ResponseEntity<User> UserByUsername(@PathVariable String username){
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        } return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<User> updateUserToAdmin(@PathVariable String username){
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            user.get().updateAdmin(roleRepository);
            return ResponseEntity.ok(user.get());
        } return ResponseEntity.notFound().build();
    }
}
