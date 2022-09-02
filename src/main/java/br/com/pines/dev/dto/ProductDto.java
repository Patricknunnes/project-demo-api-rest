package br.com.pines.dev.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductDto {

    @NotBlank(message = "uriImg is mandatory")
    private String uriImg;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Price is mandatory")
    private BigDecimal price;
}
