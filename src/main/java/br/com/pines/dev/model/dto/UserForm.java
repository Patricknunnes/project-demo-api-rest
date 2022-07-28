package br.com.pines.dev.model.dto;

import br.com.pines.dev.model.User;
import br.com.pines.dev.repository.RoleRepository;

public class UserForm {

    private String username;

    private String password;

    public UserForm(){

    }

    public UserForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User conversor(UserForm userForm) {
        return new User(userForm.getUsername(), userForm.getPassword());
    }
}