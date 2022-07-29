package br.com.pines.dev.model.dto;

import br.com.pines.dev.model.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public class ProductDto {

    private String uriImg;

    private String name;

    private String description;

    private BigDecimal price;

    public ProductDto(String uriImg, String name, String description, BigDecimal price) {
        this.uriImg = uriImg;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDto(ProductDto productDto){

    }

    public ProductDto(Product product) {
        this.uriImg = product.getUriImg();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public String getUriImg() {
        return uriImg;
    }

    public void setUriImg(String uriImg) {
        this.uriImg = uriImg;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product conversor() {
        return new Product(this.uriImg, this.name, this.description, this.price);
    }

    public static Page<ProductDto> conversorDtoList(Page<Product> products) {
        return products.map(ProductDto::new);
    }
}
