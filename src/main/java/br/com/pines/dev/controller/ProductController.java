package br.com.pines.dev.controller;


import br.com.pines.dev.model.Product;
import br.com.pines.dev.model.dto.ProductDto;
import br.com.pines.dev.repository.ProductRepository;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;


    @GetMapping
    @Transactional
    public Page<ProductDto> listProducts(@RequestParam(required = false) String name,
                                         @PageableDefault(page = 0, size = 15,
                                                 sort = "registrationDate", direction = Sort.Direction.DESC) Pageable pageable){
        if (name==null) {
            Page<Product> products = productRepository.findAll(pageable);
            return ProductDto.conversorDtoList(products);
        } else {
            Page<Product> products = productRepository.findByName(name, pageable);
            return ProductDto.conversorDtoList(products);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductDto> productById(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            ProductDto productDto = product.get().conversor();
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<ProductDto> registrationProduct(@RequestBody ProductDto productDto) {
        Product product = productDto.conversor();
        productRepository.save(product);
        return ResponseEntity.ok(productDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productRepository.findById(id)
                .map(updatedProduct -> {
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            return ResponseEntity.ok().body(productRepository.save(updatedProduct));
        }).orElse(ResponseEntity.notFound().build());
    }
}