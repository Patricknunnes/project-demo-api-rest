package br.com.pines.dev.service;

import br.com.pines.dev.dto.UserDto;
import br.com.pines.dev.model.User;
import br.com.pines.dev.repository.RoleRepository;
import br.com.pines.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplements implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(UserDto userDto) {
        return userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(roleRepository.findAll())
                .build());
    }

    @Override
    public void delete(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(String username) {
        User user = userRepository.findById(username).orElseThrow(RuntimeException::new);
        return user;
    }

    @Override
    public void saveWithAdmin(UserDto userDto) {
    }

    @Override
    public User updateToAdmin(String username) {
        return null;
    }
}
