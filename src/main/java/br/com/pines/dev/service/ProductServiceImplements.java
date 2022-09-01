package br.com.pines.dev.service;

import br.com.pines.dev.dto.ProductDto;
import br.com.pines.dev.model.Product;
import br.com.pines.dev.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImplements implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        return product;
    }

    @Override
    public Product save(ProductDto productDto) {

        return productRepository.save(Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .registrationDate(LocalDate.now())
                .uriImg(productDto.getUriImg())
                .build());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setUriImg(productDto.getUriImg());

        return productRepository.save(product);
    }
}
