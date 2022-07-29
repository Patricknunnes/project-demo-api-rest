package br.com.pines.dev.model;

import br.com.pines.dev.model.dto.ProductDto;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    public Product(){
    }
    public Product(String uriImg, String name, String description, BigDecimal price) {
        this.uriImg = uriImg;
        this.name = name;
        this.description = description;
        this.price = price;
        this.registrationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ProductDto conversor() {
        return new ProductDto(this.uriImg, this.name, this.description, this.price);
    }

    public String getUriImg() {
        return uriImg;
    }

    public void setUriImg(String uriImg) {
        this.uriImg = uriImg;
    }

}