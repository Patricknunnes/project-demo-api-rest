package br.com.pines.dev.service;

import br.com.pines.dev.dto.ProductDto;
import br.com.pines.dev.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getById(Long id);

    Product save(ProductDto productDto);

    void delete(Long id);

    Product update(Long id, ProductDto productDto);
}
