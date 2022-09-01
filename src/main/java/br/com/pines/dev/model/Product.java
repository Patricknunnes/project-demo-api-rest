package br.com.pines.dev.model;

import br.com.pines.dev.dto.ProductDto;
import lombok.*;
import org.springframework.boot.convert.DataSizeUnit;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TB_PRODUCT")
public class Product implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uriImg;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate registrationDate;
}