package br.com.pines.dev.service;

import br.com.pines.dev.dto.UserDto;
import br.com.pines.dev.model.User;

import java.util.List;

public interface UserService {

    User save(UserDto userDto);
    void delete(String username);
    List<User> getAll();
    User getById(String username);
    User updateToAdmin(String username);
    User saveWithAdmin(UserDto userDto);
}
