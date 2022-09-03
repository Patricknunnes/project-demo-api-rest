package br.com.pines.dev.service;

import br.com.pines.dev.dto.ProductDto;
import br.com.pines.dev.exception.IdNotFoundException;
import br.com.pines.dev.model.Product;
import br.com.pines.dev.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImplements productServiceImplements;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Product product = Product.builder()
            .id(1L)
            .name("Mouse")
            .description("Gamer")
            .price(new BigDecimal(3200.0))
            .registrationDate(LocalDate.now())
            .uriImg("mouse.html")
            .build();

    ProductDto productDto = ProductDto.builder()
            .name("Mouse")
            .description("Gamer")
            .price(new BigDecimal(3200.0))
            .uriImg("mouse.html")
            .build();

    Optional<Product> optionalProduct = Optional.of(product);

    @Test
    @DisplayName("Deve retornar uma lista de produtos com sucesso")
    void shouldReturnAListOfAllProductsSuccessfully() {
        List<Product> products = List.of(product, product);

        given(productRepository.findAll()).willReturn(products);

        List<Product> savedProducts = productServiceImplements.getAll();

        verify(productRepository).findAll();
        assertThat(savedProducts).isNotNull();
        assertThat(savedProducts.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve retornar um produto com sucesso")
    void shouldReturnAProductByIdSuccessfully() {
        when(productRepository.findById(product.getId())).thenReturn(optionalProduct);

        Product findedProduct = productServiceImplements.getById(1L);

        verify(productRepository).findById(anyLong());
        assertThat(findedProduct.getName()).isEqualTo(optionalProduct.get().getName());
    }

    @Test
    @DisplayName("Deve salvar um produto com sucesso")
    void shouldCreateAProductSuccessfully() {
        when(productRepository.save(any())).thenReturn(product);

        Product response = productServiceImplements.save(productDto);

        verify(productRepository).save(any());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve atualizar um produto com sucesso")
    void shouldUpdateAProductByIdSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(optionalProduct);
        when(productRepository.save(any())).thenReturn(optionalProduct.get());
        productDto.setName("test successfully");

        Product updatedProduct = productServiceImplements.update(product.getId(), productDto);

        assertThat(updatedProduct.getName()).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("test successfully");
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    void shouldDeleteAProductByIdSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(optionalProduct);

        productServiceImplements.delete(optionalProduct.get().getId());

        verify(productRepository).findById(anyLong());
    }
}