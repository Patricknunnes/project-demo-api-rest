package br.com.pines.dev.controller;


import br.com.pines.dev.model.Product;
import br.com.pines.dev.model.dto.ProductDto;
import br.com.pines.dev.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
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
    public List<ProductDto> listProducts(@RequestParam(required = false) String name){
        List<ProductDto> productsDto = new ArrayList<>();
        if (name==null) {
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                productsDto.add(product.conversor(product));
            }
        } else {
            List<Product> products = productRepository.findByName(name);
            for (Product product: products) {
                productsDto.add(product.conversor(product));
            }
        }
        return productsDto;
    }

    @GetMapping("/{id}")
    @Transactional
    public ProductDto productById(@PathVariable Long id){
        Product product = productRepository.getReferenceById(id);
        ProductDto productDto = product.conversor(product);
        return productDto;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<ProductDto> registrationProduct(@RequestBody ProductDto productDto) {
        Product product = productDto.conversor(productDto);
        productRepository.save(product);
        return ResponseEntity.ok(productDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product p) {

        return productRepository.findById(id)
                .map(m -> {
            m.setName(p.getName());
            m.setDescription(p.getDescription());
            m.setPrice(p.getPrice());
            Product product = productRepository.save(m);
            return ResponseEntity.ok().body(product);
        }).orElse(ResponseEntity.notFound().build());
    }
}
