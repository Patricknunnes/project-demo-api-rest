package br.com.pines.dev.service;

import br.com.pines.dev.dto.ProductDto;
import br.com.pines.dev.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> get(Long id, String name, String description);

    Product getById(Long id);

    Product save(ProductDto productDto);

    void delete(Long id);

    Product update(Long id, ProductDto productDto);
}
