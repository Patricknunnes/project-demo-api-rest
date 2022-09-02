package br.com.pines.dev.service;

import br.com.pines.dev.dto.UserDto;
import br.com.pines.dev.exception.IdNotFoundException;
import br.com.pines.dev.model.Role;
import br.com.pines.dev.model.User;
import br.com.pines.dev.repository.RoleRepository;
import br.com.pines.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplements implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(UserDto userDto) {
        Role role = roleRepository.findById(2L).orElseThrow();

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        return userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(roles)
                .build());
    }

    @Override
    public void delete(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userRepository.deleteById(username);
        }
        throw new IdNotFoundException();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(String username) {
        User user = userRepository.findById(username).orElseThrow(IdNotFoundException::new);
        return user;
    }

    @Override
    public User saveWithAdmin(UserDto userDto) {
        Role role = roleRepository.findById(1L).orElseThrow();

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        return userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(roles)
                .build());
    }

    @Override
    public User updateToAdmin(String username) {
        User user = userRepository.findById(username).orElseThrow(IdNotFoundException::new);

        Role role = roleRepository.findById(1L).orElseThrow();
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setRoles(roles);

        return userRepository.save(user);
    }
}
