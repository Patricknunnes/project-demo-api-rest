package br.com.pines.dev.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDto {

    private String message;
    private Instant timestamp;
}
