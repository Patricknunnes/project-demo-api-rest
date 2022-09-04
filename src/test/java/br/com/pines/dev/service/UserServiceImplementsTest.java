package br.com.pines.dev.service;

import br.com.pines.dev.dto.UserDto;
import br.com.pines.dev.model.Role;
import br.com.pines.dev.model.User;
import br.com.pines.dev.model.enums.RoleName;
import br.com.pines.dev.repository.RoleRepository;
import br.com.pines.dev.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplementsTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    UserServiceImplements userServiceImplements;
    Role role = Role.builder()
            .id(2L)
            .roleName(RoleName.ROLE_USER)
            .build();
    Optional<Role> optionalRole = Optional.of(role);
    User user = User.builder()
            .username("rox")
            .password("1234")
            .roles(List.of(role))
            .build();
    Optional<User> optionalUser = Optional.of(user);
    UserDto userDto = UserDto.builder()
            .username("rox")
            .password("1234")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    void shouldSaveAUserSuccessfully() {
        when(roleRepository.findById(anyLong())).thenReturn(optionalRole);
        when(userRepository.save(any())).thenReturn(user);

        User savedUser = userServiceImplements.save(userDto);

        verify(roleRepository).findById(anyLong());
        verify(userRepository).save(any());
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("Deve deletar um usuário com sucesso")
    void shouldDeleteAUserSuccessfully() {
        when(userRepository.findById(anyString())).thenReturn(optionalUser);

        userServiceImplements.delete(optionalUser.get().getUsername());

        verify(userRepository).findById(optionalUser.get().getUsername());
    }

    @Test
    @DisplayName("Deve retornar uma lista de usuários com sucesso")
    void shouldReturnAListOfAllUsersSuccessfully() {
        List<User> users = List.of(user, user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userServiceImplements.getAll();

        verify(userRepository).findAll();
        assertThat(allUsers).isNotNull();
        assertThat(allUsers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve retornar um usuário com sucesso")
    void shouldReturnAUserByIdSuccessfully() {
        when(userRepository.findById(anyString())).thenReturn(optionalUser);

        User findedUser = userServiceImplements.getById(user.getUsername());

        verify(userRepository).findById(optionalUser.get().getUsername());
        assertThat(findedUser.getUsername()).isEqualTo(optionalUser.get().getUsername());
    }

    @Test
    @DisplayName("Deve atualizar a role de um usuário para admin com sucesso")
    void shouldUpdateAUserToAdmin() {
        when(userRepository.findById(anyString())).thenReturn(optionalUser);
        when(roleRepository.findById(anyLong())).thenReturn(optionalRole);
        role.setId(1L);
        role.setRoleName(RoleName.ROLE_ADMIN);
        user.setRoles(List.of(role));

        userServiceImplements.updateToAdmin(user.getUsername());

        verify(userRepository).findById(optionalUser.get().getUsername());
        verify(roleRepository).findById(optionalRole.get().getId());
        assertThat(user.getRoles().get(0).getRoleName()).isEqualTo(RoleName.ROLE_ADMIN);
    }
}