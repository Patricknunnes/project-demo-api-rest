package br.com.pines.dev.controller;

import br.com.pines.dev.dto.UserDto;
import br.com.pines.dev.model.User;
import br.com.pines.dev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User save(@RequestBody @Valid UserDto userDto) {
        return userService.save(userDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}")
    public void delete(@PathVariable String username) {
        userService.delete(username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{username}")
    public User getById(@PathVariable String username) {
        return userService.getById(username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}")
    public User updateToAdmin(@PathVariable String username) {
        return userService.updateToAdmin(username);
    }

    @PostMapping("/admin")
    public User saveWithAdmin(@RequestBody @Valid UserDto userDto) {
        return userService.saveWithAdmin(userDto);
    }
}
