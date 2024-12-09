package br.com.pines.dev.controller;


import br.com.pines.dev.dto.ProductDto;
import br.com.pines.dev.model.Product;
import br.com.pines.dev.service.ProductService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Product>> get(@RequestParam (required = false) Long id,
                                            @RequestParam (required = false) String name,
                                            @RequestParam (required = false) String description) {
        Page<Product> products = productService.get(id, name, description);

        List<Product> products1 = products.getContent();

        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products1);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    @Transactional
    public Product save(@Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return productService.update(id, productDto);
    }
}