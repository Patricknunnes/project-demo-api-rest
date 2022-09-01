package br.com.pines.dev.dto;

import br.com.pines.dev.model.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {

    private String username;

    private String password;
}
